package com.example.movies2app.data.local.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.example.movies2app.data.remote.retrofit.PersonItems
import com.example.movies2app.data.remote.retrofit.TvItems

@Dao
interface MoviesDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMovie(movieItems: MoviesItems)
    @Delete
    suspend fun deleteMovie(moviesItem: MoviesItems)
    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MoviesItems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTv(tvItems: TvItems)
    @Delete
    suspend fun deleteTv(tvItems: TvItems)
    @Query("SELECT * FROM tv")
    fun getAllTv(): LiveData<List<TvItems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPerson(personItems: PersonItems)
    @Delete
    suspend fun deletePerson(personItems: PersonItems)
    @Query("SELECT * FROM person")
    fun getAllPerson(): LiveData<List<PersonItems>>
}