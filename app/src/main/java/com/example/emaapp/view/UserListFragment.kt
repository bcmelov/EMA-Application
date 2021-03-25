package com.example.emaapp.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.emaapp.R
import com.example.emaapp.data.DataSource

//fragment with display of list of the attendees as RecyclerViewer
class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Adapter(DataSource.users)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        rv.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.users_filter, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        adapter.users = when (item.itemId) {
            R.id.item_all -> DataSource.users
            R.id.item_android -> DataSource.android_users
            R.id.item_iOS -> DataSource.iOS_users
            else -> throw IllegalStateException("Invalid option.")
        }
        return super.onOptionsItemSelected(item)
    }
}


