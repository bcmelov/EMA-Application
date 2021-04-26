package com.example.emaapp.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.emaapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorPageFragment : Fragment(R.layout.error_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonError).setOnClickListener {
            findNavController().navigate(R.id.action_errorPageFragment2_to_userListFragment)
        }
    }
}