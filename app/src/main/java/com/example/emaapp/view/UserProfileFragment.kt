package com.example.emaapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.emaapp.R
import com.example.emaapp.api.RetrofitBuilder
import com.example.emaapp.api.Service
import com.example.emaapp.model.UserProfileData
import com.example.emaapp.utils.Status
import com.example.emaapp.view.viewModels.DetailViewModel
import com.example.emaapp.view.viewModels.DetalViewModelFactory


class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    companion object {
        const val KEY_NAME = "id"
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var bundleId: String

    //parsing function for user icon URI
    fun parseSlashedUri(value: String) = Uri.parse(value.replace("\\", ""))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //    receiving ID from the bundle (working)
        bundleId = arguments?.getString(KEY_NAME) ?: throw IllegalStateException("No name in args")
        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            DetalViewModelFactory(Service(RetrofitBuilder.apiService))
        ).get(DetailViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getUser(bundleId).observe(viewLifecycleOwner, Observer {
            val progressBar = ProgressBar(context)
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resource.data?.let { user -> retrieveProfile(user) }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        layoutInflater.inflate(R.layout.fragment_user_list, null)
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


    //LOADING DATA IN USER PROFILE
    @SuppressLint("SetTextI18n")
    private fun retrieveProfile(user: UserProfileData) {

//ICON GLIDE
        val userIcon = view?.findViewById<ImageView>(R.id.user_icon)
        if (userIcon != null) {
            Glide.with(userIcon.context)
                .load(user.icon192)
                .into(userIcon)
        }
//HEADER
        //view?.findViewById<ImageView>(R.id.user_icon)?.setImageResource(student.displayIcon)
        view?.findViewById<TextView>(R.id.user_name)?.setText(user.name)
        view?.findViewById<TextView>(R.id.platform_name)?.text = user.participantType

        //media buttons (Slack cannot be opened on the Emulator because of missing Slack application, email and linkedIn currently not provided by API)
        view?.findViewById<ImageButton>(R.id.slack_icon)?.setOnClickListener {
            val url = Uri.parse(user.slackURL)
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }
        view?.findViewById<ImageButton>(R.id.email_icon)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(user.email))
            startActivity(Intent.createChooser(intent, getString(R.string.email_client)))
        }
        view?.findViewById<ImageButton>(R.id.linkedin_icon)?.setOnClickListener {
            val url = Uri.parse(user.linkedIn)
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }

//SKILLS
        //beginning levels of skills
        view?.findViewById<ProgressBar>(R.id.progressBarAndroid)?.progress = 0
        view?.findViewById<ProgressBar>(R.id.progressBarKotlin)?.progress = 0
        view?.findViewById<ProgressBar>(R.id.progressBariOS)?.progress = 0
        view?.findViewById<ProgressBar>(R.id.progressBarSwift)?.progress = 0

        //updating skills based on profile information
        var i = 0
        var androidSkill = 0
        var kotlinSkill = 0
        var iosSkill = 0
        var swiftSkill = 0
        while (i < user.skills.size) {
            when (user.skills[i].skillType) {
                "android" -> androidSkill = user.skills[i].value
                "kotlin" -> kotlinSkill = user.skills[i].value
                "ios" -> iosSkill = user.skills[i].value
                "swift" -> swiftSkill = user.skills[i].value
            }
            i++
        }

        view?.findViewById<ProgressBar>(R.id.progressBarAndroid)?.progress = androidSkill * 10
        view?.findViewById<TextView>(R.id.progress_android)?.text = "${androidSkill}/10"
        view?.findViewById<ProgressBar>(R.id.progressBarKotlin)?.progress = kotlinSkill * 10
        view?.findViewById<TextView>(R.id.progress_kotlin)?.text = "${kotlinSkill}/10"
        view?.findViewById<ProgressBar>(R.id.progressBariOS)?.progress = iosSkill * 10
        view?.findViewById<TextView>(R.id.progress_iOS)?.text = "${iosSkill}/10"
        view?.findViewById<ProgressBar>(R.id.progressBarSwift)?.progress = swiftSkill * 10
        view?.findViewById<TextView>(R.id.progress_swift)?.text = "${swiftSkill}/10"


//HOMEWORK
        //1. homework
        when (user.homework[0].state) {
            "acceptance" -> {
                view?.findViewById<TextView>(R.id.title_push_1)?.setState(true)
                view?.findViewById<TextView>(R.id.title_review_1)?.setState(true)
                view?.findViewById<TextView>(R.id.title_acceptance_1)?.setState(true)
            }
            "review" -> {
                view?.findViewById<TextView>(R.id.title_review_1)?.setState(true)
                view?.findViewById<TextView>(R.id.title_push_1)?.setState(true)
            }
            "push" -> {
                view?.findViewById<TextView>(R.id.title_push_1)?.setState(true)
            }
        }

        //2. homework
        when (user.homework[1].state) {
            "acceptance" -> {
                view?.findViewById<TextView>(R.id.title_push_2)?.setState(true)
                view?.findViewById<TextView>(R.id.title_review_2)?.setState(true)
                view?.findViewById<TextView>(R.id.title_acceptance_2)?.setState(true)
            }
            "review" -> {
                view?.findViewById<TextView>(R.id.title_push_2)?.setState(true)
                view?.findViewById<TextView>(R.id.title_review_2)?.setState(true)
            }
            "push" -> {
                view?.findViewById<TextView>(R.id.title_push_2)?.setState(true)
            }
        }

        //3. homework
        when (user.homework[2].state) {
            "acceptance" -> {
                view?.findViewById<TextView>(R.id.title_push_3)?.setState(true)
                view?.findViewById<TextView>(R.id.title_review_3)?.setState(true)
                view?.findViewById<TextView>(R.id.title_acceptance_3)?.setState(true)
            }
            "review" -> {
                view?.findViewById<TextView>(R.id.title_push_3)?.setState(true)
                view?.findViewById<TextView>(R.id.title_review_3)?.setState(true)
            }
            "push" -> {
                view?.findViewById<TextView>(R.id.title_push_3)?.setState(true)
            }
        }

        //4. homework
        when (user.homework[3].state) {
            "acceptance" -> {
                view?.findViewById<TextView>(R.id.title_push_4)?.setState(true)
                view?.findViewById<TextView>(R.id.title_review_4)?.setState(true)
                view?.findViewById<TextView>(R.id.title_acceptance_4)?.setState(true)
            }
            "review" -> {
                view?.findViewById<TextView>(R.id.title_push_4)?.setState(true)
                view?.findViewById<TextView>(R.id.title_review_4)?.setState(true)
            }
            "push" -> {
                view?.findViewById<TextView>(R.id.title_push_4)?.setState(true)
            }
        }

        // 5. homework
        when (user.homework[4].state) {
            "acceptance" -> {
                view?.findViewById<TextView>(R.id.title_push_5)?.setState(true)
                view?.findViewById<TextView>(R.id.title_review_5)?.setState(true)
                view?.findViewById<TextView>(R.id.title_acceptance_5)?.setState(true)
            }
            "review" -> {
                view?.findViewById<TextView>(R.id.title_push_5)?.setState(true)
                view?.findViewById<TextView>(R.id.title_review_5)?.setState(true)
            }
            "push" -> {
                view?.findViewById<TextView>(R.id.title_push_5)?.setState(true)
            }
        }

        //6. homework
        when (user.homework[5].state) {
            "acceptance" -> {
                view?.findViewById<TextView>(R.id.title_push_6)?.setState(true)
                view?.findViewById<TextView>(R.id.title_review_6)?.setState(true)
                view?.findViewById<TextView>(R.id.title_acceptance_5)?.setState(true)
            }
            "review" -> {
                view?.findViewById<TextView>(R.id.title_push_6)?.setState(true)
                view?.findViewById<TextView>(R.id.title_review_6)?.setState(true)
            }
            "push" -> {
                view?.findViewById<TextView>(R.id.title_push_6)?.setState(true)
            }
        }
    }

    private fun TextView.setState(done: Boolean) {
        val id = if (done) R.drawable.ic_done else R.drawable.ic_waiting
        setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0)
    }
}


