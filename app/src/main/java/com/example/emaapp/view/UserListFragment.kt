package com.example.emaapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.emaapp.R
import com.example.emaapp.api.RetrofitBuilder
import com.example.emaapp.api.Service
import com.example.emaapp.model.LoginResponse
import com.example.emaapp.model.User
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.utils.LoginContract
import com.example.emaapp.utils.Status.*
import com.example.emaapp.view.viewModels.MainViewModel
import com.example.emaapp.view.viewModels.ViewModelFactory
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.coroutines.launch
import okhttp3.Response
import retrofit2.HttpException


//fragment with display of list of the attendees as RecyclerViewer
class UserListFragment() : Fragment(R.layout.fragment_user_list), ViewHolder.UserClickListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ViewHolder.UserAdapter
    private val appPreferences: AppPreferences by lazy { AppPreferences(requireContext()) }
    private lateinit var loginResult: ActivityResultLauncher<LoginResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginResult = registerForActivityResult(LoginContract()) {
            if (it != null) {
                lifecycleScope.launch {
                    appPreferences.setToken(LoginContract.TOKEN)
                }
                setupObservers()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()

        adapter = ViewHolder.UserAdapter(arrayListOf(), this)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        rv.adapter = adapter

        val toggleButton = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup)
        toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val type = when (checkedId) {
                    R.id.button_android -> getString(R.string.android_student)
                    R.id.button_iOs -> getString(R.string.ios_student)
                    R.id.button_all -> null
                    else -> throw java.lang.IllegalStateException("$checkedId")
                }
                val all = viewModel.getUsers(appPreferences.getToken()).value?.data.orEmpty()
                val list = if (type != null) {
                    all.filter { it.participantType == type }
                } else {
                    all
                }
                retrieveList(list)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(Service(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupObservers() {
        val progressBar = view?.findViewById<ProgressBar>(R.id.progressBarUserList)
        viewModel.getUsers("").observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    LOADING -> {
                        progressBar?.visibility = View.VISIBLE
                        Log.d("TAG", "LOADING")
                    }
                    SUCCESS -> {
                        progressBar?.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    ERROR -> {
                        progressBar?.visibility = View.GONE
//                         (HttpException(this).code() == 401) {
                            loginResult.launch(LoginResponse(appPreferences.getToken()))
                            Log.d("TAG", "FAILURE")
//                            findNavController().navigate(R.id.action_userListFragment_to_errorPageFragment2)
                    }
                }
            }
        })
    }

    private fun retrieveList(users: List<User>) {
        adapter.apply {
            addUsers(users)
            notifyDataSetChanged()
        }
    }

    //Bundle with UserID
    override fun onUserClick(user: User) {
        val bundle = Bundle()
        bundle.putString(UserProfileFragment.KEY_NAME, user.id)
        findNavController().navigate(R.id.action_userListFragment_to_userProfileFragment4, bundle)
    }
}
