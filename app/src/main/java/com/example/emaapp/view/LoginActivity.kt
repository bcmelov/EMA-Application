package com.example.emaapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.emaapp.R
import com.example.emaapp.api.LoginRetrofitBuilder.apiService
import com.example.emaapp.api.Service
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.utils.Status
import com.example.emaapp.view.viewModels.LoginViewModel
import com.example.emaapp.view.viewModels.LoginViewModelFactory
import com.thekhaeng.pushdownanim.PushDownAnim


class LoginActivity() : AppCompatActivity(R.layout.login_activity) {

    private lateinit var button: Button
    private lateinit var usernameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var viewModel: LoginViewModel
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()

        button = findViewById(R.id.loginButton)
        usernameText = findViewById(R.id.inputUserName)
        passwordText = findViewById(R.id.inputPassword)

        //temporary 'button' for login (test purposes -> on button click inserts the credentials)
        val icon = findViewById<ImageView>(R.id.etneteraIcon)
        icon.setOnClickListener {
            usernameText.setText("cmelova.b")
            passwordText.setText("ursispal09")
        }

        PushDownAnim.setPushDownAnimTo(button)
            .setOnClickListener {
                username = usernameText.text.toString()
                password = passwordText.text.toString()
                setupObservers()
            }
    }

    private fun setupViewModel() {
        appPreferences = AppPreferences(this)
        viewModel = ViewModelProviders.of(
            this,
            LoginViewModelFactory(Service(apiService), appPreferences)
        ).get(LoginViewModel::class.java)
    }


    private fun setupObservers() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBarUserProfile)
        viewModel.loginUser(username, password).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        progressBar?.visibility = View.VISIBLE
                        Log.d("TAG", "LOADING")
                    }
                    Status.SUCCESS -> {
                        progressBar?.visibility = View.GONE
                        Log.d("TAG", "SUCCESS")
                        setupViewModel()
                        setResult(RESULT_OK, intent)
                        finish()
                        Toast.makeText(this,
                            getString(R.string.login_success),
                            Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        progressBar?.visibility = View.GONE
                        Toast.makeText(this,
                            getString(R.string.wrong_credentials),
                            Toast.LENGTH_LONG).show()
                        Log.d("TAG", "FAILURE")
                    }
                }
            }
        })
    }
}