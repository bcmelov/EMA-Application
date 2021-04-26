package com.example.emaapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.emaapp.R
import com.example.emaapp.data.Skill
import com.example.emaapp.databinding.EditProfileBinding
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.utils.Status
import com.example.emaapp.view.viewModels.EditViewModel
import com.thekhaeng.pushdownanim.PushDownAnim
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditFragment : Fragment(R.layout.edit_profile) {

    private val viewModel: EditViewModel by viewModels()

    @Inject
    lateinit var appPreferences: AppPreferences

    private lateinit var id: String
    private lateinit var skills: Skill

    private lateinit var iosSkill: String
    private lateinit var swiftSkill: String
    private lateinit var androidSkill: String
    private lateinit var kotlinSkill: String

    private var iosSkillInt: Int = 0
    private var swiftSkillInt: Int = 0
    private var androidSkillInt: Int = 0
    private var kotlinSkillInt: Int = 0


    //View Binding - nullable and non nullable
    private var _binding: EditProfileBinding? = null
    private val binding get() = _binding!!

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

        id = appPreferences.getId()


        //TODO - check for non int values -> else return toast with warning
        //onClickListener for LoginButton
        PushDownAnim.setPushDownAnimTo(binding.submitEdit)
            .setOnClickListener {
                iosSkill = binding.iosEdit.text.toString()
                swiftSkill = binding.swiftEdit.text.toString()
                androidSkill = binding.androidEdit.text.toString()
                kotlinSkill = binding.kotlinEdit.text.toString()

                if (iosSkill != "") {
                    iosSkillInt = iosSkill.toInt()
                }
                if (swiftSkill != "") {
                    swiftSkillInt = swiftSkill.toInt()
                }
                if (androidSkill != "") {
                    androidSkillInt = androidSkill.toInt()
                }
                if (kotlinSkill != "") {
                    kotlinSkillInt = kotlinSkill.toInt()
                }

                skills = Skill(swiftSkillInt, iosSkillInt, kotlinSkillInt, androidSkillInt)
                setupObservers()
            }
    }

    private fun setupObservers() {
        viewModel.editSkills(id, skills).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        Log.d("TAG", "LOADING")
                    }
                    Status.SUCCESS -> {
                        resource.data?.let { user ->
                            findNavController().navigate(R.id.action_editFragment_to_userListFragment)
                        }
                        Log.d("TAG", "SUCCESS")
                    }
                    Status.ERROR -> {
                        Log.d("TAG", "FAILURE")
                        findNavController().navigate(R.id.action_editFragment_to_errorPageFragment2)
                    }
                }
            }
        })
    }

}