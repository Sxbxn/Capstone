package com.kyonggi.cellification.ui.di

import com.kyonggi.cellification.data.remote.service.UserService
import com.kyonggi.cellification.repository.user.datasource.UserRemoteDataSource
import com.kyonggi.cellification.repository.user.datasourceImpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun providesUserRemoteDataSource(
        userService: UserService
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userService)
    }
}