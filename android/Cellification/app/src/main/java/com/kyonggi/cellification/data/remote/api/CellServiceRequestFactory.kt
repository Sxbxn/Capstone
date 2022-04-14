package com.kyonggi.cellification.data.remote.api

import com.kyonggi.cellification.data.remote.service.CellService
import com.kyonggi.cellification.utils.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CellServiceRequestFactory {
    private const val baseUrl = ""
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
            AuthInterceptor()
        }).build()

    val retrofit: CellService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(CellService::class.java)
}