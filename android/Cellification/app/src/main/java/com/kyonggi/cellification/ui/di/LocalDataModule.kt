package com.kyonggi.cellification.ui.di

import com.kyonggi.cellification.data.local.dao.CellDao
import com.kyonggi.cellification.repository.cell.datasource.CellLocalDataSource
import com.kyonggi.cellification.repository.cell.datasourceImpl.CellLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDataModule {
    @Singleton
    @Provides
    fun providesCellLocalDataSource(
        cellDao: CellDao
    ): CellLocalDataSource {
        return CellLocalDataSourceImpl(cellDao)
    }
}