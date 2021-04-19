package com.example.emaapp.view

import android.app.Activity
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.emaapp.R
import com.example.emaapp.api.LoginRetrofitBuilder.apiService
import com.example.emaapp.api.Service
import com.example.emaapp.model.LoginRequest
import com.example.emaapp.utils.LoginContract
import com.example.emaapp.utils.Resource
import com.example.emaapp.view.viewModels.LoginViewModel
import com.example.emaapp.view.viewModels.LoginViewModelFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginActivity : AppCompatActivity(R.layout.login_activity) {

    private lateinit var button: Button
    private lateinit var usernameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var viewModel: LoginViewModel
    private lateinit var username: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()

        button = findViewById(R.id.loginButton)
        usernameText = findViewById(R.id.inputUserName)
        passwordText = findViewById(R.id.inputPassword)

        //temporary 'button' for login (test purposes)
        val icon = findViewById<ImageView>(R.id.loginIcon)
        icon.setOnClickListener() {
            usernameText.setText("cmelova.b")
            passwordText.setText("ursispal09")
        }

        button.setOnClickListener {
            username = usernameText.text.toString()
            password = passwordText.text.toString()
            setupObservers()
//            lifecycleScope.launch {
//                try {
//                    setupViewModel()
//                    setResult(Activity.RESULT_OK, intent)
//                    finish()
//                } catch (ie: IOException) {
//                    Toast.makeText(applicationContext,
//                        getString(R.string.error_title),
//                        Toast.LENGTH_SHORT).show()
//                } catch (http: HttpException) {
//                    when (http.code()) {
//                        403 -> Toast.makeText(applicationContext,
//                            getString(R.string.wrong_credentials),
//                            Toast.LENGTH_LONG).show()
//                        401 -> Toast.makeText(applicationContext,
//                            "Access denied.",
//                            Toast.LENGTH_LONG).show()
//                        else -> Toast.makeText(applicationContext,
//                            getString(R.string.error_title),
//                            Toast.LENGTH_LONG).show()
//                    }
//                }
//            }

        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            LoginViewModelFactory(Service(apiService))
        ).get(LoginViewModel::class.java)
    }

    private fun setupObservers() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBarUserProfile)
        try {
            viewModel.loginUser(username,password)
            setupViewModel()
            setResult(Activity.RESULT_OK, intent)
            finish()
        } catch (ie: IOException) {
            Toast.makeText(applicationContext,
                getString(R.string.error_title),
                Toast.LENGTH_SHORT).show()
        } catch (http: HttpException) {
            when (http.code()) {
                403 -> Toast.makeText(applicationContext,
                    getString(R.string.wrong_credentials),
                    Toast.LENGTH_LONG).show()
                401 -> Toast.makeText(applicationContext,
                    "Access denied.",
                    Toast.LENGTH_LONG).show()
                else -> Toast.makeText(applicationContext,
                    getString(R.string.error_title),
                    Toast.LENGTH_LONG).show()
            }

            viewModel.loginResourceData.observe(this, Observer { resource ->
                when (resource) {
                    Resource.loading(T) -> {
                        progressBar?.visibility = View.VISIBLE
                        Log.d("TAG", "LOADING")
                    }
                    Resource.success(T) -> {
                        progressBar?.visibility = View.GONE
                        Log.d("TAG", "SUCCESS")
                        setupViewModel()
                        setResult(Activity.RESULT_OK)
                        finish()
                        Toast.makeText(this,
                            getString(R.string.login_success),
                            Toast.LENGTH_LONG).show()
                    }
                    Resource.error(T, "An error occurred.") -> {
                        progressBar?.visibility = View.GONE
                        Toast.makeText(this,
                            getString(R.string.wrong_credentials),
                            Toast.LENGTH_LONG).show()
                        Log.d("TAG", "FAILURE")
                    }
                }
            })
        }
    }
}


//    // storing the HTTP response (access_token)
//    object TokenRepository {
//        var token: String = ""
//    }
//}