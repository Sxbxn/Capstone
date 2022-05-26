package com.kyonggi.cellification.ui.di

import com.kyonggi.cellification.data.remote.service.CellService
import com.kyonggi.cellification.data.remote.service.UserService
import com.kyonggi.cellification.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetModule {

    @Singleton
    @Provides
    fun providesUserService(okHttpClient: OkHttpClient): UserService {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://9d1e-118-34-210-82.jp.ngrok.io/")
            .client(okHttpClient)
            .build().create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun providesCellService(okHttpClient: OkHttpClient): CellService {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://9d1e-118-34-210-82.jp.ngrok.io/")
            .client(okHttpClient.also {
                AuthInterceptor()
            })
            .build().create(CellService::class.java)
    }

}