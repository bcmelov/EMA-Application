package com.example.emaapp.view

import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.emaapp.R
import dagger.hilt.android.AndroidEntryPoint


//NOTE - CLASS IS NOT YET FINISHED (PART OF HW6 - TODO)

@AndroidEntryPoint
class EditFragment: Fragment(R.layout.edit_profile) {

    //KEYNAME to retrieve the user information from the bundle
    companion object {
        const val KEY_NAME = "id"
    }

    private val androidInput = view?.findViewById<EditText>(R.id.android_edit)
    private val kotlinInput = view?.findViewById<EditText>(R.id.kotlin_edit)
    private val iosInput = view?.findViewById<EditText>(R.id.ios_edit)
    private val swiftInput = view?.findViewById<EditText>(R.id.swift_edit)

    private val androidSkill = androidInput?.text.toString()
    private val kotlinSkill = kotlinInput?.text.toString()
    private val iosSkill = iosInput?.text.toString()
    private val swiftSkill = swiftInput?.text.toString()

}