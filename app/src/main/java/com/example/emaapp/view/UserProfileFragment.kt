package com.example.emaapp.view

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.emaapp.R
import com.example.emaapp.data.UserProfileData
import com.example.emaapp.database.Database
import com.example.emaapp.database.FavUserDao
import com.example.emaapp.database.FavUserEntity
import com.example.emaapp.databinding.FragmentUserProfileBinding
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.utils.Status
import com.example.emaapp.view.viewModels.ProfileViewModel
import com.thekhaeng.pushdownanim.PushDownAnim
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserProfileFragment: Fragment(R.layout.fragment_user_profile) {

    private lateinit var bundleId: String
    private lateinit var database: Database
    private lateinit var favDao: FavUserDao
    private var favUser = false

    private val viewModel: ProfileViewModel by viewModels()
    @Inject
    lateinit var appPreferences : AppPreferences

    //View Binding - nullable and non nullable
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    //KEYNAME to retrieve the user information from the bundle
    companion object {
        const val KEY_NAME = "id"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //database with favourite users
        database = Database.getDatabase(requireActivity().applicationContext)
        favDao = database.userDao()

        //receiving ID from the bundle
        bundleId = arguments?.getString(KEY_NAME) ?: throw IllegalStateException("No id in args.")

        //go to edit profile
        binding.editButton.setOnClickListener {
            if (appPreferences.getId() == bundleId) {
                findNavController().navigate(R.id.action_userProfileFragment_to_editFragment)
            } else {
                Toast.makeText(context, getString(R.string.no_rights_edit), Toast.LENGTH_SHORT).show()
            }
        }

        //check, whether the user is in fav database
        lifecycleScope.launch(Dispatchers.IO) {
            favUser = favDao.isFavourite(bundleId)
            setButtonState()
        }

        setupObservers()

        //Fav button - adding/removing users from favourites
        PushDownAnim.setPushDownAnimTo(binding.favButton)
            .setOnClickListener {
                if (!favUser) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        favDao.insert(FavUserEntity(bundleId))
                        favUser = true
                        setButtonState()
                    }
                    Log.d("TAG", "User added to favourites.")
                } else {
                    lifecycleScope.launch(Dispatchers.IO) {
                        favDao.delete(FavUserEntity(bundleId))
                        favUser = false
                        setButtonState()
                    }
                    Log.d("TAG", "User removed from favourites.")
                }
            }
    }

    @SuppressLint("InflateParams")
    private fun setupObservers() {
        viewModel.getUser(bundleId).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressBarUserProfile?.visibility = View.VISIBLE
                        Log.d("TAG", "LOADING")
                    }
                    Status.SUCCESS -> {
                        binding.progressBarUserProfile?.visibility = View.GONE
                        resource.data?.let { user ->
                            retrieveProfile(user)
                        }
                        Log.d("TAG", "SUCCESS")
                    }
                    Status.ERROR -> {
                        binding.progressBarUserProfile?.visibility = View.GONE
                        Log.d("TAG", "FAILURE")
                        findNavController().navigate(R.id.action_userProfileFragment_to_errorPageFragment2)
                    }
                }
            }
        })
    }

    //Loading data in user profile
    @SuppressLint("SetTextI18n")
    private fun retrieveProfile(user: UserProfileData) {

//User icon Glide
        Glide.with(binding.userIcon.context)
            .load(user.icon192)
            .error(R.drawable.no_image_icon)
            .placeholder(R.drawable.loading_icon)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.userIcon)

//Header
        binding.userName.text = user.name
        binding.platformName.text = user.participantType.toString()

        //media buttons
        binding.slackIcon.setOnClickListener {
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

        binding.emailIcon.setOnClickListener {
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

        binding.linkedinIcon.setOnClickListener {
            try {
                val url = user.linkedIn?.let { Uri.parse(it) }
                    ?: return@setOnClickListener (Toast.makeText(context,
                        getString(R.string.error_linked_in),
                        Toast.LENGTH_SHORT)
                        .show())
                val intent = Intent(Intent.ACTION_VIEW, url)
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, getString(R.string.activity_not_found), Toast.LENGTH_SHORT)
                    .show()
            }
        }

//Homework
        //1. homework
        when (user.homework[0].state) {
            "acceptance" -> {
                binding.titlePush1.setState(true)
                binding.titleReview1.setState(true)
                binding.titleAcceptance1.setState(true)
            }
            "review" -> {
                binding.titleReview1.setState(true)
                binding.titlePush1.setState(true)
                binding.titleAcceptance1.setState(false)
            }
            "push" -> {
                binding.titlePush1.setState(true)
                binding.titleReview1.setState(false)
                binding.titleAcceptance1.setState(false)
            }
            "comingsoon" -> {
                binding.titlePush1.setState(false)
                binding.titleReview1.setState(false)
                binding.titleAcceptance1.setState(false)
            }
        }

        //2. homework
        when (user.homework[1].state) {
            "acceptance" -> {
                binding.titlePush2.setState(true)
                binding.titleReview2.setState(true)
                binding.titleAcceptance2.setState(true)
            }
            "review" -> {
                binding.titlePush2.setState(true)
                binding.titleReview2.setState(true)
                binding.titleAcceptance2.setState(false)
            }
            "push" -> {
                binding.titlePush2.setState(true)
                binding.titleReview2.setState(false)
                binding.titleAcceptance2.setState(false)
            }
            "comingsoon" -> {
                binding.titlePush2.setState(false)
                binding.titleReview2.setState(false)
                binding.titleAcceptance2.setState(false)
            }
        }

        //3. homework
        when (user.homework[2].state) {
            "acceptance" -> {
                binding.titlePush3.setState(true)
                binding.titleReview3.setState(true)
                binding.titleAcceptance3.setState(true)
            }
            "review" -> {
                binding.titlePush3.setState(true)
                binding.titleReview3.setState(true)
                binding.titleAcceptance3.setState(false)
            }
            "push" -> {
                binding.titlePush3.setState(true)
                binding.titleReview3.setState(false)
                binding.titleAcceptance3.setState(false)
            }
            "comingsoon" -> {
                binding.titlePush3.setState(false)
                binding.titleReview3.setState(false)
                binding.titleAcceptance3.setState(false)
            }
        }

        //4. homework
        when (user.homework[3].state) {
            "acceptance" -> {
                binding.titlePush4.setState(true)
                binding.titleReview4.setState(true)
                binding.titleAcceptance4.setState(true)
            }
            "review" -> {
                binding.titlePush4.setState(true)
                binding.titleReview4.setState(true)
                binding.titleAcceptance4.setState(false)
            }
            "push" -> {
                binding.titlePush4.setState(true)
                binding.titleReview4.setState(false)
                binding.titleAcceptance4.setState(false)
            }
            "comingsoon" -> {
                binding.titlePush4.setState(false)
                binding.titleReview4.setState(false)
                binding.titleAcceptance4.setState(false)
            }
        }

        // 5. homework
        when (user.homework[4].state) {
            "acceptance" -> {
                binding.titlePush5.setState(true)
                binding.titleReview5.setState(true)
                binding.titleAcceptance5.setState(true)
            }
            "review" -> {
                binding.titlePush5.setState(true)
                binding.titleReview5.setState(true)
                binding.titleAcceptance5.setState(false)
            }
            "push" -> {
                binding.titlePush5.setState(true)
                binding.titleReview5.setState(false)
                binding.titleAcceptance5.setState(false)
            }
            "comingsoon" -> {
                binding.titlePush5.setState(false)
                binding.titleReview5.setState(false)
                binding.titleAcceptance5.setState(false)
            }
        }

        //6. homework
        when (user.homework[5].state) {
            "acceptance" -> {
                binding.titlePush6.setState(true)
                binding.titleReview6.setState(true)
                binding.titleAcceptance6.setState(true)
            }
            "review" -> {
                binding.titlePush6.setState(true)
                binding.titleReview6.setState(true)
                binding.titleAcceptance6.setState(false)
            }
            "push" -> {
                binding.titlePush6.setState(true)
                binding.titleReview6.setState(false)
                binding.titleAcceptance6.setState(false)
            }
            "comingsoon" -> {
                binding.titlePush6.setState(false)
                binding.titleReview6.setState(false)
                binding.titleAcceptance6.setState(false)
            }
        }

        //Skills
        //beginning level of skills
        binding.progressBarAndroid.progress = 0
        binding.progressBarKotlin.progress = 0
        binding.progressBariOS.progress = 0
        binding.progressBarSwift.progress = 0

        //updating skills based on profile information
        val kotlinSkill = user.skills?.kotlin
        val androidSkill = user.skills?.android
        val iosSkill = user.skills?.ios
        val swiftSkill = user.skills?.swift

        if (androidSkill != null) {
            binding.progressBarAndroid.progress = androidSkill * 10
            binding.progressAndroid.text = "${androidSkill}/10"
        }
        if (kotlinSkill != null) {
            binding.progressBarKotlin.progress = kotlinSkill * 10
            binding.progressKotlin.text = "${kotlinSkill}/10"
        }

        if (iosSkill != null) {
            binding.progressBariOS.progress = iosSkill * 10
            binding.progressIOS.text = "${iosSkill}/10"
        }

        if (swiftSkill != null) {
            binding.progressBarSwift.progress = swiftSkill * 10
            binding.progressSwift.text = "${swiftSkill}/10"
        }
    }


    private fun TextView.setState(done: Boolean) {
        val id = if (done) R.drawable.ic_done else R.drawable.ic_waiting
        setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0)
    }

    //parse damaged Slack URI from API
    private fun parseSlashedUri(value: String) = Uri.parse(value.replace("\\", ""))


    //favourite button
    private fun setButtonState() {
        if (favUser) {
            binding.favButton.setBackgroundResource(R.drawable.fav_button_full)
            Log.d("Tag", "user is in favourites")
        } else {
            binding.favButton.setBackgroundResource(R.drawable.fav_button_empty)
            Log.d("Tag", "user not found in favourites")
        }
    }

    //setting ViewBinding back to null
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



