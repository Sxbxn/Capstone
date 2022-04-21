package com.kyonggi.cellification.utils

import com.kyonggi.cellification.ui.di.App
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + App.prefs.token ?: "")
                .build()
        return chain.proceed(request)
    }
}