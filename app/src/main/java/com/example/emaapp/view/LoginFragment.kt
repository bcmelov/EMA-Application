package com.example.emaapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.emaapp.R
import com.example.emaapp.api.RetrofitBuilder
import com.example.emaapp.api.Service
import com.example.emaapp.model.User
import com.example.emaapp.utils.Status
import com.example.emaapp.view.viewModels.LoginViewModel
import com.example.emaapp.view.viewModels.LoginViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginFragment : Fragment(R.layout.login_activity) {
    private lateinit var button: Button
    private lateinit var usernameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var viewModel: LoginViewModel
    private var username: String = "cmelova.b"
    private var password: String = "ursispal09"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        button = view.findViewById(R.id.loginButton)
        usernameText = view.findViewById(R.id.inputUserName)
        passwordText = view.findViewById(R.id.inputPassword)

        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            LoginViewModelFactory(Service(RetrofitBuilder.apiService))
        ).get(LoginViewModel::class.java)
    }

    @SuppressLint("InflateParams")
    private fun setupObservers() {
        button.setOnClickListener {
            username = usernameText.text.toString()
            password = passwordText.text.toString()
            findNavController().navigate(R.id.action_loginFragment_to_userListFragment)
        }
        val progressBar = view?.findViewById<ProgressBar>(R.id.progressBarUserProfile)
        viewModel.loginUser(username, password).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        progressBar?.visibility = View.VISIBLE
                        Log.d("TAG", "LOADING")
                    }
                    Status.SUCCESS -> {
                        progressBar?.visibility = View.GONE
                        resource.data?.let { token -> retrieveToken(token) }
                    }
                    Status.ERROR -> {
                        progressBar?.visibility = View.GONE
                        Log.d("TAG", "FAILURE")
                    }
                }
            }
        })
    }

    //LOADING DATA IN USER PROFILE
    @SuppressLint("SetTextI18n")
    private fun retrieveToken(token: Unit) {

    }
}