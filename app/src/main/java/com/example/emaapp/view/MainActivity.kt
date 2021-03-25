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
import javax.sql.DataSource


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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