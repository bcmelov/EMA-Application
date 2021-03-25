package com.example.emaapp.view

import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.emaapp.R
import com.google.android.material.button.MaterialButtonToggleGroup
import javax.sql.DataSource


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var toolbar: Toolbar
    private lateinit var toggleGroup: MaterialButtonToggleGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        toggleGroup.addOnButtonCheckedListener { toggleGroup, checkedId, isChecked ->
//            if (isChecked) {
//                when (checkedId) {
//                    R.id.button_all -> showToast("Left Align")
//                    R.id.button_android -> showToast("Center Align")
//                    R.id.button_android -> showToast("Right Align")
//                }

                // access to navController that is a part of NavHostFragment
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment).navController

        // setup toolbar with nav controller
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(
            navController,
            AppBarConfiguration(navController.graph)
        )
    }
}