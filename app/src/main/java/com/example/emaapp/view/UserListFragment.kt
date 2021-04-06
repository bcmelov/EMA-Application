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

//        val service = UserService(createRetrofit().create(UserApi::class.java))
        adapter = ViewHolder.UserAdapter(arrayListOf(), this)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        rv.adapter = adapter


        val toggleButton = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup)
        toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val participantType = when (checkedId) {
                    R.id.button_android -> "androidStudent"
                    R.id.button_iOs -> "iosStudent"
                    R.id.button_all -> "androidMentor"
                    else -> throw java.lang.IllegalStateException("$checkedId")
                }

//                adapter.users = when (checkedId) {
//                    //TODO
////                    R.id.button_all -> DataSource.users
////                    R.id.button_android -> DataSource.android_users
////                    R.id.button_iOs -> DataSource.iOS_users
//                    else -> throw IllegalStateException("$checkedId")
////                }
//                }
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
//                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
//                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Log.d("TAG", "FAILURE")
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
//                        recyclerView.visibility = View.GONE
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

    override fun onUserClick(user: User) {
        val bundle = Bundle()
//        bundle.putString(UserProfileFragment.KEY_NAME,  getString(user.id.toInt()) /*previous version: user.name ... however an int is required)*/
//        )
        bundle.putString("id", user.id)
        bundle.putString("name", user.name)
        bundle.putString("icon", user.icon192)
        bundle.putString("platform", user.participantType)
        bundle.putString("slack", user.slackURL)

//        //skills values
//        var j: Int = 0
//        while (j < user.skills.size) {
//            when (user.skills[j].skillType) {
//                "android" -> bundle.putInt("android", user.skills[0].value)
//                "ios" -> bundle.putInt("ios", user.skills[0].value)
//                "kotlin" -> bundle.putInt("kotlin", user.skills[0].value)
//                "swift" -> bundle.putInt("swift", user.skills[0].value)
//            }
//            j++
//        }



        findNavController().navigate(R.id.action_userListFragment_to_userProfileFragment4, bundle)
    }

//    private fun fetchWithCallback(service: UserService, participantType: String?) {
//        service.getCharacters(participantType).enqueue(object : Callback<Result> {
//            override fun onResponse(call: Call<Result>, response: Response<Result>) {
//                showSuccess(response.body())
//            }
//
//            override fun onFailure(call: Call<Result>, t: Throwable) {
//                showFailure()
//            }
//        })
//    }

//////    CURRENTLY NOT BEING USED
//    private fun fetchWithCoroutines(service: UserService, participantType: String?) {
//        lifecycleScope.launch {
//            try {
//                val result = service.suspendGetCharacters(participantType)
//                showSuccess(result)
//            } catch (ex: IOException) {
//                showFailure()
//            }
//        }
//    }

//    private fun showSuccess(result: Result?) {
////        loading?.visibility = View.GONE (loading is currently not being used in test phase)
//        recyclerView.isVisible = true
//        emptyText?.isVisible = false
//
//        Log.d("TAG", "SUCCESS")
//        if (result != null) {
//            recyclerView.adapter = UserAdapter(result.results, this)
//        }
//    }
//
//    private fun showFailure() {
////        loading?.visibility = View.GONE (loading is currently not being used in test phase)
//        recyclerView.isVisible = false
//        emptyText?.isVisible = true
//        Log.d("TAG", "FAILURE")
//    }
//

}
