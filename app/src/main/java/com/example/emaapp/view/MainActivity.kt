package com.example.emaapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.emaapp.R


class MainActivity : AppCompatActivity(R.layout.activity_main) {
//    object onClickListener : View.OnClickListener {
//        override fun onClick(v: View?) {
//            TODO("Not yet implemented")
//        }
//
//    }

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // access to navController that is a part of NavHostFragment
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment).navController

        // setup toolbar with nav controller
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(
            navController,
            AppBarConfiguration(navController.graph)
        )


//        //BUTTON FOR NIGHT/DAY MODE
//        /* declare the switch from the layout file */
//        val btn = findViewById<SwitchMaterial>(R.id.switch1)
//
//        /* set the switch to listen on checked change */
//        btn.setOnCheckedChangeListener { _, isChecked ->
//
//            /*if the button is checked, enable dark mode, else keep day mode*/
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
    }
}