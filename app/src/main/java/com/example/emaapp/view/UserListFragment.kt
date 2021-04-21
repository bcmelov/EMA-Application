package com.example.emaapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.emaapp.R
import com.example.emaapp.api.RetrofitBuilder
import com.example.emaapp.api.Service
import com.example.emaapp.data.ParticipantType
import com.example.emaapp.data.User
import com.example.emaapp.model.LoginResponse
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.utils.LoginContract
import com.example.emaapp.utils.Status.*
import com.example.emaapp.view.viewModels.MainViewModel
import com.example.emaapp.view.viewModels.ViewModelFactory
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.coroutines.launch


//fragment with display of list of the attendees as RecyclerViewer
class UserListFragment : Fragment(R.layout.fragment_user_list),
    ViewHolder.UserClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ViewHolder.UserAdapter
    private val appPreferences: AppPreferences by lazy { AppPreferences(requireContext()) }
    private lateinit var loginResult: ActivityResultLauncher<LoginResponse>
    private lateinit var swipe: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //registering activity as result activity for LoginContract()
        loginResult = registerForActivityResult(LoginContract()) {
            setupViewModel()
            viewModel.getUsers()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()

        viewModel.getUsers()

        adapter = ViewHolder.UserAdapter(arrayListOf(), this)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        rv.adapter = adapter

        //Refresh feature
        swipe = view.findViewById(R.id.swipeLayout_items)
        swipe.setOnRefreshListener {
            setupViewModel()
            viewModel.getUsers()
        }


        val toggleButton = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup)
        toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            val all = viewModel.usersData.value?.data.orEmpty()
            if (isChecked) {
                val list = when (checkedId) {
                    R.id.button_android -> all.filter { it.participantType == ParticipantType.ANDROID_STUDENT || it.participantType == ParticipantType.ANDROID_MENTOR }
                    R.id.button_iOs -> all.filter { it.participantType == ParticipantType.IOS_STUDENT || it.participantType == ParticipantType.IOS_MENTOR }
                    R.id.button_all -> all
                    else -> throw java.lang.IllegalStateException("$checkedId")
                }
                retrieveList(list)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(Service(RetrofitBuilder(appPreferences).apiService), appPreferences)
        ).get(MainViewModel::class.java)
    }


    private fun setupObservers() {
        val progressBar = view?.findViewById<ProgressBar>(R.id.progressBarUserList)
        viewModel.usersData.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    LOADING -> {
                        progressBar?.visibility = View.VISIBLE
                        Log.d("TAG", "LOADING")
                    }
                    SUCCESS -> {
                        progressBar?.visibility = View.GONE
                        Log.d("TAG", "SUCCESS")
                        resource.data?.let { users ->
                            retrieveList(users)
                            swipe.isRefreshing = false
                        }
                    }
                    ERROR -> {
                        progressBar?.visibility = View.GONE
                        lifecycleScope.launch {
                            if (appPreferences.getToken() == "") {
                                lifecycleScope.launch {
                                    loginResult.launch(LoginResponse(appPreferences.getToken()))
                                }
                            } else {
                                Log.d("TAG", "FAILURE")
                                swipe.isRefreshing = false
                                findNavController().navigate(R.id.action_userListFragment_to_errorPageFragment2)
                            }
                        }
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
