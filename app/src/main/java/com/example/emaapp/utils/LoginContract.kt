package com.example.emaapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.emaapp.model.LoginResponse
import com.example.emaapp.view.LoginActivity

class LoginContract : ActivityResultContract<LoginResponse, LoginResponse?>() {

    //key for intent extras
    companion object {
        private const val TOKEN = "access_token"
    }

    //explicit intent
    override fun createIntent(context: Context, input: LoginResponse?): Intent {
        return Intent(context, LoginActivity::class.java).apply {}
    }

    override fun parseResult(resultCode: Int, intent: Intent?): LoginResponse? =
        if (resultCode == Activity.RESULT_OK && intent != null) {
            val token = intent.getStringExtra(TOKEN) ?: ""
            LoginResponse(token)
        } else {
            null
        }
}