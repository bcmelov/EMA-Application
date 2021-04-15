package com.example.emaapp.view

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.emaapp.R
import com.example.emaapp.api.RetrofitBuilder
import com.example.emaapp.api.Service
import com.example.emaapp.database.Database
import com.example.emaapp.database.FavUserDao
import com.example.emaapp.database.FavUserEntity
import com.example.emaapp.model.UserProfileData
import com.example.emaapp.utils.Status
import com.example.emaapp.view.viewModels.DetailViewModel
import com.example.emaapp.view.viewModels.DetailViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    //KEYNAME to retrieve the user information from the bundle
    companion object {
        const val KEY_NAME = "id"
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var bundleId: String
    private lateinit var favButton: Button
    private lateinit var database: Database
    private lateinit var favDao: FavUserDao
    private var isFavourite = false

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favButton = view.findViewById(R.id.fav_button)

        //database with the favourite users
        database = Database.getDatabase(requireActivity().applicationContext)
        favDao = database.userDao()

        //receiving ID from the bundle
        bundleId = arguments?.getString(KEY_NAME) ?: throw IllegalStateException("No id in args.")

        setupViewModel()
        setupObservers()
        isFavourite()
        setButtonState()

//        //TODO - SET THE BUTTON TO REFLECT THE STATE OF FAV EVEN AFTER LEAVING THE FRAGMENT (isPressed bellow is not working)

        favButton.setOnClickListener {
            if (!isFavourite) {
                lifecycleScope.launch(Dispatchers.IO) {
                    favDao.insert(FavUserEntity(bundleId))
                    isFavourite = true
                }
                Log.d("TAG", "USER ADDED TO FAVOURITES")
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    favDao.delete(FavUserEntity(bundleId))
                    isFavourite = false
                }
                Log.d("TAG", "USER REMOVED FROM FAVOURITES")
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            DetailViewModelFactory(Service(RetrofitBuilder.apiService))
        ).get(DetailViewModel::class.java)
    }

    @SuppressLint("InflateParams")
    private fun setupObservers() {
        val progressBar = view?.findViewById<ProgressBar>(R.id.progressBarUserProfile)
        viewModel.getUser(bundleId).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        progressBar?.visibility = View.VISIBLE
                        Log.d("TAG", "LOADING")
                    }
                    Status.SUCCESS -> {
                        progressBar?.visibility = View.GONE
                        resource.data?.let { user ->
                            retrieveProfile(user)
                        }
                    }
                    Status.ERROR -> {
                        progressBar?.visibility = View.GONE
                        Log.d("TAG", "FAILURE")
                        findNavController().navigate(R.id.action_userProfileFragment_to_errorPageFragment2)
                    }
                }
            }
        })
    }

    //LOADING DATA IN USER PROFILE
    @SuppressLint("SetTextI18n")
    private fun retrieveProfile(user: UserProfileData) {

//USER ICON GLIDE
        val userIcon = view?.findViewById<ImageView>(R.id.user_icon)
        if (userIcon != null) {
            Glide.with(userIcon.context)
                .load(user.icon192)
                .error(R.drawable.no_image_icon)
                .placeholder(R.drawable.loading_icon)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(userIcon)
        }
//HEADER
        view?.findViewById<TextView>(R.id.user_name)?.text = user.name
        view?.findViewById<TextView>(R.id.platform_name)?.text = user.participantType

        //media buttons
        view?.findViewById<ImageButton>(R.id.slack_icon)?.setOnClickListener {
            try {
                val url = user.slackURL?.let { parseSlashedUri(it) }
                    ?: return@setOnClickListener (Toast.makeText(context,
                        getString(R.string.error_slack),
                        Toast.LENGTH_LONG).show())
                val intent = Intent(Intent.ACTION_VIEW, url)
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, getString(R.string.activity_not_found), Toast.LENGTH_LONG)
                    .show()
            }
        }
        view?.findViewById<ImageButton>(R.id.email_icon)?.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(user.email))
                startActivity(Intent.createChooser(intent, getString(R.string.email_client)))
            } catch (e: NullPointerException) {
                Toast.makeText(context, getString(R.string.error_email), Toast.LENGTH_LONG).show()
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, getString(R.string.activity_not_found), Toast.LENGTH_LONG)
                    .show()
            }
        }
        view?.findViewById<ImageButton>(R.id.linkedin_icon)?.setOnClickListener {
            try {
                val url = user.linkedIn?.let { Uri.parse(it) }
                    ?: return@setOnClickListener (Toast.makeText(context,
                        getString(R.string.error_linked_in),
                        Toast.LENGTH_LONG)
                        .show())
                val intent = Intent(Intent.ACTION_VIEW, url)
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, getString(R.string.activity_not_found), Toast.LENGTH_LONG)
                    .show()
            }
        }

//SKILLS
        //beginning level of skills
        view?.findViewById<ProgressBar>(R.id.progressBarAndroid)?.progress = 0
        view?.findViewById<ProgressBar>(R.id.progressBarKotlin)?.progress = 0
        view?.findViewById<ProgressBar>(R.id.progressBariOS)?.progress = 0
        view?.findViewById<ProgressBar>(R.id.progressBarSwift)?.progress = 0

//        //updating skills based on profile information
//        var i = 0
//        var androidSkill = 0
//        var kotlinSkill = 0
//        var iosSkill = 0
//        var swiftSkill = 0
//        while (i < user.skills.size) {
//            when (user.skills[i].skillType) {
//                "android" -> androidSkill = user.skills[i].value
//                "kotlin" -> kotlinSkill = user.skills[i].value
//                "ios" -> iosSkill = user.skills[i].value
//                "swift" -> swiftSkill = user.skills[i].value
//            }
//            i++
//        }
//        view?.findViewById<ProgressBar>(R.id.progressBarAndroid)?.progress = androidSkill * 10
//        view?.findViewById<TextView>(R.id.progress_android)?.text = "${androidSkill}/10"
//        view?.findViewById<ProgressBar>(R.id.progressBarKotlin)?.progress = kotlinSkill * 10
//        view?.findViewById<TextView>(R.id.progress_kotlin)?.text = "${kotlinSkill}/10"
//        view?.findViewById<ProgressBar>(R.id.progressBariOS)?.progress = iosSkill * 10
//        view?.findViewById<TextView>(R.id.progress_iOS)?.text = "${iosSkill}/10"
//        view?.findViewById<ProgressBar>(R.id.progressBarSwift)?.progress = swiftSkill * 10
//        view?.findViewById<TextView>(R.id.progress_swift)?.text = "${swiftSkill}/10"

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

    //parse damaged Slack URI from API
    private fun parseSlashedUri(value: String) = Uri.parse(value.replace("\\", ""))

    private fun isFavourite() {
        lifecycleScope.launch(Dispatchers.IO) {
            isFavourite = favDao.isFavourite()
            Log.d("TAG", isFavourite.toString())
        }
    }

    private fun setButtonState() {
        if (isFavourite) {
            favButton.isPressed = true
        }
    }
}

