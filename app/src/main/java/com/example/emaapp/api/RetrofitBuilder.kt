package com.example.emaapp.api

import androidx.appcompat.app.AppCompatActivity
import com.example.emaapp.preferences.AppPreferences
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class RetrofitBuilder
@Inject constructor(private var appPreferences: AppPreferences) : AppCompatActivity() {

    //Retrofit
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //OK HTTP client
    private fun createClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    //header to prevent EOFException (https://github.com/square/okhttp/issues/1517)
                    .addHeader("Connection", "close")
                    //header to connect to server
                    .header("access_token", appPreferences.getToken())
                it.proceed(request.build())
            }
            .addInterceptor(interceptor)
            .build()
    }

    //API service
    val apiService: UserApi = getRetrofit().create(UserApi::class.java)

    //Base API URL
    companion object {
        private const val BASE_URL = "http://emarest.cz.mass-php-1.mit.etn.cz/api/"
    }

}