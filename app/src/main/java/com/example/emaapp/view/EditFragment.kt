package com.example.emaapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.emaapp.R
import com.example.emaapp.data.Skill
import com.example.emaapp.data.User
import com.example.emaapp.databinding.EditProfileBinding
import com.example.emaapp.databinding.FragmentUserProfileBinding
import com.example.emaapp.utils.Status
import com.example.emaapp.view.viewModels.EditViewModel
import com.example.emaapp.view.viewModels.ProfileViewModel
import com.thekhaeng.pushdownanim.PushDownAnim
import dagger.hilt.android.AndroidEntryPoint


//NOTE - CLASS IS NOT YET FINISHED (PART OF HW6 - TODO)

@AndroidEntryPoint
class EditFragment: Fragment(R.layout.edit_profile) {

    private val viewModel: EditViewModel by viewModels()

    //View Binding - nullable and non nullable
    private var _binding: EditProfileBinding? = null
    private val binding get() = _binding!!

    private val androidInput = view?.findViewById<EditText>(R.id.android_edit)
    private val kotlinInput = view?.findViewById<EditText>(R.id.kotlin_edit)
    private val iosInput = view?.findViewById<EditText>(R.id.ios_edit)
    private val swiftInput = view?.findViewById<EditText>(R.id.swift_edit)

    private val androidSkill = androidInput?.text.toString()
    private val kotlinSkill = kotlinInput?.text.toString()
    private val iosSkill = iosInput?.text.toString()
    private val swiftSkill = swiftInput?.text.toString()

    private lateinit var skills: Skill
    private lateinit var id: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = EditProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        skills = Skill(0,0,3,3)

        //onClickListener for LoginButton
        PushDownAnim.setPushDownAnimTo(binding.submitEdit)
            .setOnClickListener {
//                skills = binding.inputPassword.text.toString()
                setupObservers()
            }
    }

    private fun setupObservers() {
        viewModel.editSkills(id,skills).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
//                        binding.progressBarUserProfile.visibility = View.VISIBLE
                        Log.d("TAG", "LOADING")
                    }
                    Status.SUCCESS -> {
//                        binding.progressBarUserProfile.visibility = View.GONE
                        resource.data?.let { user ->
                            findNavController().navigate(R.id.action_editFragment_to_userListFragment)
                        }
                        Log.d("TAG", "SUCCESS")
                    }
                    Status.ERROR -> {
//                        binding.progressBarUserProfile.visibility = View.GONE
                        Log.d("TAG", "FAILURE")
                        findNavController().navigate(R.id.action_editFragment_to_errorPageFragment2)
                    }
                }
            }
        })
    }

}