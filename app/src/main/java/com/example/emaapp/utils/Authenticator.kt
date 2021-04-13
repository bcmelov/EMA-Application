package com.example.emaapp.utils

import com.example.emaapp.view.LoginFragment
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


class AppAuthenticator(
    private val tokensProvider: LoginFragment.ResponseStorage
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return response.request.newBuilder().header("AccessToken", tokensProvider.response.access_token).build()
    }
}
