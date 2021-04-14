package com.example.emaapp.api

import com.example.emaapp.utils.AppAuthenticator
import com.example.emaapp.view.LoginFragment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "http://emarest.cz.mass-php-1.mit.etn.cz/api/"

    //RETROFIT
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //OK HTTP CLIENT
    private fun createClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder().header("access_token", LoginFragment.TokenRepository().token).build()
                it.proceed(request)
            }
            .authenticator(AppAuthenticator(LoginFragment.TokenRepository()))
            .addInterceptor(interceptor)
            .build()
    }

    //API SERVICE
    val apiService: UserApi = getRetrofit().create(UserApi::class.java)
}