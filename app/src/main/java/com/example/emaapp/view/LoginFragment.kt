package com.example.emaapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import com.example.emaapp.R
import com.example.emaapp.api.LoginRetrofitBuilder.apiService
import com.example.emaapp.api.Service
import com.example.emaapp.model.LoginRequest
import com.example.emaapp.model.LoginResponse
import com.example.emaapp.utils.Status
import com.example.emaapp.view.viewModels.LoginViewModel
import com.example.emaapp.view.viewModels.LoginViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.login_activity) {
    private lateinit var button: Button
    private lateinit var usernameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var viewModel: LoginViewModel
    private lateinit var username: String
    private lateinit var password: String
    val responseStorage = ResponseStorage()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        button = view.findViewById(R.id.loginButton)
        usernameText = view.findViewById(R.id.inputUserName)
        passwordText = view.findViewById(R.id.inputPassword)

        button.setOnClickListener {
            username = usernameText.text.toString()
            password = passwordText.text.toString()
            setupObservers()
        }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            LoginViewModelFactory(Service(apiService))
        ).get(LoginViewModel::class.java)
    }

    @SuppressLint("InflateParams")
    private fun setupObservers() {
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
                        //TODO
                        extractResponse()
                        Log.d("TAG", "SUCCESS")
                        println(responseStorage.response.access_token)
//                        findNavController().navigate(R.id.action_loginFragment_to_userListFragment)
                    }
                    Status.ERROR -> {
                        progressBar?.visibility = View.GONE
                        Log.d("TAG", "FAILURE")
                    }
                }
            }
        })
    }

    //TODO
    // storing the HTTP response (expiration and access_token)
    class ResponseStorage {
        var response: LoginResponse = LoginResponse("","")
    }

    private fun extractResponse() {
            lifecycleScope.launch {
                responseStorage.response = apiService.suspendLoginUser(LoginRequest(username,password))
            }
    }
}