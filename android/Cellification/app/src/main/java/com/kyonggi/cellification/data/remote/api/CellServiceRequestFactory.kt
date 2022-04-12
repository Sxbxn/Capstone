package com.kyonggi.cellification.data.remote.api

import com.kyonggi.cellification.data.remote.service.CellService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CellServiceRequestFactory {
    private const val baseUrl = ""

    val retrofit: CellService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }).build())
        .build()
        .create(CellService::class.java)
}