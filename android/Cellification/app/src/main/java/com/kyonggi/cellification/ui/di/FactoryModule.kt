package com.kyonggi.cellification.ui.di

import com.kyonggi.cellification.repository.user.UserRepository
import com.kyonggi.cellification.ui.viewmodel.UserViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        userRepository: UserRepository
    ): UserViewModelFactory {
        return UserViewModelFactory(
            userRepository
        )
    }
}