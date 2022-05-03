package com.kyonggi.cellification.ui.di

import android.content.Context
import androidx.room.Room
import com.kyonggi.cellification.data.local.db.CellDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DBModule {
    @Singleton
    @Provides
    fun provideCellDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            CellDatabase::class.java,
            "cell_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCellDao(appDatabase: CellDatabase) = appDatabase.cellDao()
}