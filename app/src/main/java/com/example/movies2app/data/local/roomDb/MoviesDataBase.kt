package com.example.movies2app.data.local.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.example.movies2app.data.remote.retrofit.MoviesList
import com.example.movies2app.data.remote.retrofit.PersonItems
import com.example.movies2app.data.remote.retrofit.TvItems

@Database(
    entities = [MoviesItems::class, TvItems::class , PersonItems::class],
    version = 12,
    exportSchema = false
)
abstract class MoviesDataBase : RoomDatabase() {
    abstract val dao: MoviesDao

}