package com.kyonggi.cellification.ui.di

import com.kyonggi.cellification.repository.cell.CellRepository
import com.kyonggi.cellification.repository.cell.CellRepositoryImpl
import com.kyonggi.cellification.repository.cell.datasource.CellLocalDataSource
import com.kyonggi.cellification.repository.cell.datasource.CellRemoteDataSource
import com.kyonggi.cellification.repository.user.UserRepository
import com.kyonggi.cellification.repository.user.UserRepositoryImpl
import com.kyonggi.cellification.repository.user.datasource.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideNewCellRepository(
        cellRemoteDataSource: CellRemoteDataSource,
        cellLocalDataSource: CellLocalDataSource
    ): CellRepository {
        return CellRepositoryImpl(cellRemoteDataSource,cellLocalDataSource)
    }
}