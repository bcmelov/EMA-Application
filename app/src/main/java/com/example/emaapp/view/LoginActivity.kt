package com.example.emaapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.emaapp.R
import com.example.emaapp.api.RetrofitBuilder
import com.example.emaapp.api.Service
import com.example.emaapp.databinding.LoginActivityBinding
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.utils.Status
import com.example.emaapp.view.viewModels.LoginViewModel
import com.example.emaapp.view.viewModels.LoginViewModelFactory
import com.thekhaeng.pushdownanim.PushDownAnim
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(R.layout.login_activity) {

    private lateinit var viewModel: LoginViewModel
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //View Binding
        val binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        //temporary 'button' for login (test purposes -> on button click inserts the credentials)
        binding.etneteraIcon.setOnClickListener {
            binding.inputUserName.setText("cmelova.b")
            binding.inputPassword.setText("ursispal09")
        }


        //onClickListener for LoginButton
        PushDownAnim.setPushDownAnimTo(binding.loginButton)
            .setOnClickListener {
                username = binding.inputUserName.text.toString()
                password = binding.inputPassword.text.toString()
                setupObservers()
            }
    }

    private fun setupViewModel() {
        appPreferences = AppPreferences(this)
        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(Service(RetrofitBuilder(appPreferences).apiService),
                appPreferences)
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
//                        setupViewModel()
                        viewModel
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