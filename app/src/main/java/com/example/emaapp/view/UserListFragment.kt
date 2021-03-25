package com.example.emaapp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.emaapp.R
import com.example.emaapp.data.DataSource
import com.example.emaapp.data.User

//fragment with display of list of the attendees as RecyclerViewer
class UserListFragment : Fragment(R.layout.fragment_user_list) {
    companion object {
        private const val KEY_USERNAME = "KEY_USERNAME"
        fun newInstance(username: String): UserListFragment = UserListFragment().apply {
            arguments = bundleOf(
                KEY_USERNAME to username
            )
        }
    }

    lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        hwStars()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Adapter(DataSource.users)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        rv.adapter = adapter
//        val username = arguments?.getString(KEY_USERNAME)
    }

        private fun hwStars() {
            view?.findViewById<ImageView>(R.id.list_hw1)?.setState1(User.BaraCmelova.push1)
            view?.findViewById<ImageView>(R.id.list_hw2)?.setState2(User.BaraCmelova.push2)
            view?.findViewById<ImageView>(R.id.list_hw3)?.setState3(User.BaraCmelova.push3)
            view?.findViewById<ImageView>(R.id.list_hw4)?.setState4(User.BaraCmelova.push4)
            view?.findViewById<ImageView>(R.id.list_hw5)?.setState5(User.BaraCmelova.push5)
            view?.findViewById<ImageView>(R.id.list_hw6)?.setState6(User.BaraCmelova.push6)
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

    private fun ImageView.setState1 (done : Boolean) {
        val id = if (done) setImageResource(R.drawable.ic_one_done) else setImageResource(R.drawable.ic_one)
    }
    private fun ImageView.setState2 (done : Boolean) {
        val id= if (done) setImageResource(R.drawable.ic_two_done) else setImageResource(R.drawable.ic_two)
    }
    private fun ImageView.setState3 (done : Boolean) {
        val id= if (done) setImageResource(R.drawable.ic_three_done) else setImageResource(R.drawable.ic_three)
    }
    private fun ImageView.setState4 (done : Boolean) {
        val id= if (done) setImageResource(R.drawable.ic_four_done) else setImageResource(R.drawable.ic_four)
    }
    private fun ImageView.setState5 (done : Boolean) {
        val id= if (done) setImageResource(R.drawable.ic_five_done) else setImageResource(R.drawable.ic_five)
    }
    private fun ImageView.setState6 (done : Boolean) {
        val id= if (done) setImageResource(R.drawable.ic_six_done) else setImageResource(R.drawable.ic_six)
    }
}


