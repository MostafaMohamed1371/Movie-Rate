package com.example.movies2app.di

import android.content.Context
import androidx.room.Room
import com.example.movies2app.data.local.roomDb.MoviesDao
import com.example.movies2app.data.local.roomDb.MoviesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModuel {
    @Provides
    @Singleton
    fun getDataBase(@ApplicationContext context: Context): MoviesDataBase {
        return   Room.databaseBuilder(
            context.applicationContext,
            MoviesDataBase::class.java,
            "gyms_database"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideMealDao(dataBaseManager: MoviesDataBase): MoviesDao {
        return dataBaseManager.dao
    }

}