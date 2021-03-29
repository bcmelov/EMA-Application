package com.example.emaapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.emaapp.R
import com.example.emaapp.data.DataSource
import com.example.emaapp.data.User
import com.google.android.material.button.MaterialButtonToggleGroup

//fragment with display of list of the attendees as RecyclerViewer
class UserListFragment : Fragment(R.layout.fragment_user_list), UserClickListener {
    private lateinit var adapter: UserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter(DataSource.users, this)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        rv.adapter = adapter

        val toggleButton = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup)
        toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                adapter.users = when (checkedId) {
                    R.id.button_all -> DataSource.users
                    R.id.button_android -> DataSource.android_users
                    R.id.button_iOs -> DataSource.iOS_users
                    else -> throw IllegalStateException("$checkedId")
                }
            }
        }
    }

    override fun onUserClick(user: User) {
        val bundle = Bundle()
        bundle.putString(UserProfileFragment.KEY_NAME, getString(user.displayName)
        )
        findNavController().navigate(R.id.action_userListFragment_to_userProfileFragment4, bundle)
    }
}