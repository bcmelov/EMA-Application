package com.example.emaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        /* declare the switch from the layout file */
        val btn = findViewById<SwitchMaterial>(R.id.switch1)

        /* set the switch to listen on checked change */
        btn.setOnCheckedChangeListener { _, isChecked ->

            /*if the button is checked, enable dark mode, else keep day mode*/
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}