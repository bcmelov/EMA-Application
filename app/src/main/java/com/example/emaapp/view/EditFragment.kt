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
import com.xw.repo.BubbleSeekBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditFragment : Fragment(R.layout.edit_profile) {

    private val viewModel: EditViewModel by viewModels()

    @Inject
    lateinit var appPreferences: AppPreferences

    private lateinit var id: String
    private lateinit var skills: Skill

    private var iosSkill: Int = 0
    private var swiftSkill: Int = 0
    private var androidSkill: Int = 0
    private var kotlinSkill: Int = 0


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

        //SeekBars for skills
        //note: findViewById used because binding cannot find the element //TODO: change to binding
        val androidSeekBar = view.findViewById<BubbleSeekBar>(R.id.progress_bar_android)
        val kotlinSeekBar = view.findViewById<BubbleSeekBar>(R.id.progress_bar_kotlin)
        val iosSeekBar = view.findViewById<BubbleSeekBar>(R.id.progress_bar_ios)
        val swiftSeekBar = view.findViewById<BubbleSeekBar>(R.id.progress_bar_swift)


        //onClickListener for LoginButton
        PushDownAnim.setPushDownAnimTo(binding.submitEdit).setOnClickListener {
            iosSkill = try {
                iosSeekBar.progress
            } catch (exception: NullPointerException) {
                0
            }
            swiftSkill = try {
                swiftSeekBar.progress
            } catch (exception: NullPointerException) {
                0
            }
            androidSkill = try {
                androidSeekBar.progress
            } catch (exception: NullPointerException) {
                0
            }
            kotlinSkill = try {
                kotlinSeekBar.progress
            } catch (exception: NullPointerException) {
                0
            }

            skills = Skill(swiftSkill, iosSkill, kotlinSkill, androidSkill)
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
                        resource.data?.let { _ ->
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