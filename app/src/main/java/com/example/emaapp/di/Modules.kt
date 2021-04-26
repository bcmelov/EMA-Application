package com.example.emaapp.di

import android.content.Context
import com.example.emaapp.api.Service
import com.example.emaapp.api.UserApi
import com.example.emaapp.database.Database
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.repository.DetailRepository
import com.example.emaapp.repository.LoginRepository
import com.example.emaapp.repository.MainRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context): AppPreferences {
        return AppPreferences(app)
    }

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)


    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://emarest.cz.mass-php-1.mit.etn.cz/api/")
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(client)
        .build()

    @Singleton
    @Provides
    fun provideClient(appPreferences: AppPreferences): OkHttpClient {
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

    @Singleton
    @Provides
    fun provideUserDao(db: Database) = db.userDao()

    @Singleton
    @Provides
    fun provideMainRepository(service: Service) = MainRepository(service)

    @Singleton
    @Provides
    fun provideLoginRepository(service: Service) = LoginRepository(service)

    @Singleton
    @Provides
    fun provideDetailRepository(service: Service) = DetailRepository(service)

}