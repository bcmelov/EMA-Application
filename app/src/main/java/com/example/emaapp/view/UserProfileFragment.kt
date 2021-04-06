package com.example.emaapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.emaapp.R
import com.example.emaapp.api.Service
import com.example.emaapp.api.RetrofitBuilder
import com.example.emaapp.model.UserProfileData
import com.example.emaapp.utils.Status
import com.example.emaapp.view.viewModels.MainViewModel
import com.example.emaapp.view.viewModels.ViewModelFactory

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    //
//    companion object {
//        const val KEY_NAME = "id"
//    }

    private lateinit var viewModel: MainViewModel //test

//    receiving data from the bundle
//    private val student: User by lazy {
//        val id = arguments?.getString(KEY_NAME) ?: throw IllegalStateException("No name in args")
//        List<User>.find { it.name == id } ?: throw IllegalStateException("Student is null.")
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillHeader()
//        fillSkills()
//        fillHomework()
    }


    //TEST CODE
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(Service(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            val progressBar = ProgressBar(context)
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resource.data?.let { user -> retrieveList(user) }!!
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

    private fun retrieveList(user: UserProfileData) {
    }
//

    //END OF TEST CODE
    private fun fillHeader() {
//        view?.findViewById<ImageView>(R.id.user_icon)?.setImageResource(student.displayIcon)
//        view?.findViewById<TextView>(R.id.user_name)?.setText(student.displayName)
//        view?.findViewById<TextView>(R.id.platform_name)?.text = student.type.toString()
//        view?.findViewById<ImageButton>(R.id.slack_icon)?.setOnClickListener {
//            val url = Uri.parse(student.slack)
//            val intent = Intent(Intent.ACTION_VIEW, url)
//            startActivity(intent)

    val name = arguments?.get("name")
    val icon = arguments?.get("icon")
    val platform = arguments?.get("platform")
    val slack = arguments?.get("slack")

//    view?.findViewById<ImageView>(R.id.user_icon)?.setImageResource(student.displayIcon)

    view?.findViewById<TextView>(R.id.user_name)?.text = name.toString()
    view?.findViewById<TextView>(R.id.platform_name)?.text = platform.toString()
    view?.findViewById<ImageButton>(R.id.slack_icon)?.setOnClickListener {
        val url = Uri.parse(slack as String?)
        val intent = Intent(Intent.ACTION_VIEW, url)
        startActivity(intent)
    }
}

//
//        view?.findViewById<ImageButton>(R.id.email_icon)?.setOnClickListener {
//            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
//            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(student.email))
//            startActivity(Intent.createChooser(intent, getString(R.string.email_client)))
//        }
//        view?.findViewById<ImageButton>(R.id.linkedin_icon)?.setOnClickListener {
//            val url = Uri.parse(student.linkedIn)
//            val intent = Intent(Intent.ACTION_VIEW, url)
//            startActivity(intent)
//        }
//    }
//

//    @SuppressLint("SetTextI18n")
    private fun fillSkills() {

        val homework = arguments?.get("homework")
    Log.d("TAG", homework.toString())

//        view?.findViewById<ProgressBar>(R.id.progressBarAndroid)?.progress = android as Int * 10
//        view?.findViewById<TextView>(R.id.progress_android)?.text = "${android}/10"
//        view?.findViewById<ProgressBar>(R.id.progressBarKotlin)?.progress = kotlin as Int * 10
//        view?.findViewById<TextView>(R.id.progress_kotlin)?.text = "${kotlin}/10"
//        view?.findViewById<ProgressBar>(R.id.progressBariOS)?.progress = ios as Int * 10
//        view?.findViewById<TextView>(R.id.progress_iOS)?.text = "${ios}/10"
//        view?.findViewById<ProgressBar>(R.id.progressBarSwift)?.progress = swift as Int * 10
//        view?.findViewById<TextView>(R.id.progress_swift)?.text = "${swift}/10"
    }
//
//    private fun fillHomework() {
//
//        //1. homework
//        view?.findViewById<TextView>(R.id.title_push_1)?.setState(student.push1)
//        view?.findViewById<TextView>(R.id.title_review_1)?.setState(student.review1)
//        view?.findViewById<TextView>(R.id.title_acceptance_1)?.setState(student.accepted1)
//
//        //2. homework
//        view?.findViewById<TextView>(R.id.title_push_2)?.setState(student.push2)
//        view?.findViewById<TextView>(R.id.title_review_2)?.setState(student.review2)
//        view?.findViewById<TextView>(R.id.title_acceptance_2)?.setState(student.accepted2)
//
//        //3. homework
//        view?.findViewById<TextView>(R.id.title_push_3)?.setState(student.push3)
//        view?.findViewById<TextView>(R.id.title_review_3)?.setState(student.review3)
//        view?.findViewById<TextView>(R.id.title_acceptance_3)?.setState(student.accepted3)
//
//        //4. homework
//        view?.findViewById<TextView>(R.id.title_push_4)?.setState(student.push4)
//        view?.findViewById<TextView>(R.id.title_review_4)?.setState(student.review4)
//        view?.findViewById<TextView>(R.id.title_acceptance_4)?.setState(student.accepted4)
//
//        //5. homework
//        view?.findViewById<TextView>(R.id.title_push_5)?.setState(student.push5)
//        view?.findViewById<TextView>(R.id.title_review_5)?.setState(student.review5)
//        view?.findViewById<TextView>(R.id.title_acceptance_5)?.setState(student.accepted5)
//
//        //6. homework
//        view?.findViewById<TextView>(R.id.title_push_6)?.setState(student.push6)
//        view?.findViewById<TextView>(R.id.title_review_6)?.setState(student.review6)
//        view?.findViewById<TextView>(R.id.title_acceptance_6)?.setState(student.accepted6)
//    }
//}

    private fun TextView.setState(done: Boolean) {
        val id = if (done) R.drawable.ic_done else R.drawable.ic_waiting
        setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0)
    }
}


