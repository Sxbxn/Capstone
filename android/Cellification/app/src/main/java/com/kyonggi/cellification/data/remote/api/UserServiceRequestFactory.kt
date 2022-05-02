package com.kyonggi.cellification.data.remote.api

import com.kyonggi.cellification.data.remote.service.UserService
import com.kyonggi.cellification.utils.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserServiceRequestFactory {
    private const val baseUrl = "https://d898-1-229-120-3.ngrok.io/"
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
            AuthInterceptor()
        }).build()

    val retrofit: UserService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(UserService::class.java)
}