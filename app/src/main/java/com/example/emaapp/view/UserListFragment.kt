package com.example.emaapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.emaapp.R
import com.example.emaapp.api.Service
import com.example.emaapp.api.RetrofitBuilder
import com.example.emaapp.model.User
import com.example.emaapp.utils.Status
import com.example.emaapp.view.viewModels.MainViewModel
import com.example.emaapp.view.viewModels.ViewModelFactory
import com.google.android.material.button.MaterialButtonToggleGroup


//fragment with display of list of the attendees as RecyclerViewer
class UserListFragment : Fragment(R.layout.fragment_user_list), ViewHolder.UserClickListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ViewHolder.UserAdapter
//    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()

        adapter = ViewHolder.UserAdapter(arrayListOf(), this)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        rv.adapter = adapter


//        currently not working
        val toggleButton = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup)
        toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val participantType = when (checkedId) {
                    R.id.button_android -> "androidStudent"
                    R.id.button_iOs -> "iosStudent"
                    R.id.button_all -> "androidMentor"
                    else -> throw java.lang.IllegalStateException("$checkedId")
                }
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
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            val progressBar = ProgressBar(context)
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Log.d("TAG", "FAILURE")
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        Log.d("TAG", "LOADING")
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

    //sending user id in bundle to userProfileFragment
    override fun onUserClick(user: User) {
        val bundle = Bundle()
        bundle.putString(UserProfileFragment.KEY_NAME, user.id)
        findNavController().navigate(R.id.action_userListFragment_to_userProfileFragment4, bundle)
    }

}
