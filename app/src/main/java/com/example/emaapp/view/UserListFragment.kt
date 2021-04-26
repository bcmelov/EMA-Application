package com.example.emaapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.emaapp.R
import com.example.emaapp.data.FilterType
import com.example.emaapp.data.User
import com.example.emaapp.databinding.FragmentUserListBinding
import com.example.emaapp.model.LoginResponse
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.utils.LoginContract
import com.example.emaapp.utils.Status.*
import com.example.emaapp.view.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
//fragment with display of list of the attendees as RecyclerViewer
class UserListFragment : Fragment(R.layout.fragment_user_list),
    ViewHolder.UserClickListener {

    private lateinit var adapter: ViewHolder.UserAdapter
    private val appPreferences: AppPreferences by lazy { AppPreferences(requireContext()) }
    private lateinit var loginResult: ActivityResultLauncher<LoginResponse>

    private val viewModel: MainViewModel by viewModels()

    //View Binding - nullable and non nullable
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //registering activity as result activity for LoginContract()
        loginResult = registerForActivityResult(LoginContract()) {
            viewModel.getUsers()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()

        viewModel.getUsers()

        adapter = ViewHolder.UserAdapter(arrayListOf(), this)
        binding.recyclerView.adapter = adapter

        //Refresh feature
        binding.swipeLayoutItems.setOnRefreshListener {
            viewModel.getUsers()
        }


        binding.includeLayout.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val type = when (checkedId) {
                    R.id.button_android -> FilterType.ANDROID
                    R.id.button_iOs -> FilterType.IOS
                    R.id.button_all -> FilterType.ALL
                    else -> throw java.lang.IllegalStateException("$checkedId")
                }
                viewModel.setFilterType(type)
            }
        }
    }

    private fun setupObservers() {
        viewModel.usersData.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    LOADING -> {
                        binding.progressBarUserList.visibility = View.VISIBLE
                        Log.d("TAG", "LOADING")
                    }
                    SUCCESS -> {
                        binding.progressBarUserList.visibility = View.GONE
                        Log.d("TAG", "SUCCESS")
                        resource.data?.let { users ->
                            retrieveList(users)
                            binding.swipeLayoutItems.isRefreshing = false
                        }
                    }
                    ERROR -> {
                        binding.progressBarUserList.visibility = View.GONE
                        if (appPreferences.getToken() == "") {
                            loginResult.launch(LoginResponse(appPreferences.getToken()))
                        } else {
                            Log.d("TAG", "FAILURE")
                            binding.swipeLayoutItems.isRefreshing = false
                            findNavController().navigate(R.id.action_userListFragment_to_errorPageFragment2)
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

    //setting ViewBinding back to null
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
