package com.example.emaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        /* Declare the switch from the layout file */
        val btn = findViewById<Switch>(R.id.switch1)

        /* set the switch to listen on checked change */
        btn.setOnCheckedChangeListener { _, isChecked ->

            /*
            if the button is checked, enable dark mode, change the text to disable dark mode
            else keep the switch text to enable dark mode
            */
            if (btn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}