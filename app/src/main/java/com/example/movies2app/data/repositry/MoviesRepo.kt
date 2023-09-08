package com.example.movies2app.data.repositry

import com.example.movies2app.data.remote.retrofit.CategoryList

import com.example.movies2app.data.remote.retrofit.MoviesList
import com.example.movies2app.data.remote.retrofit.PersonList
import com.example.movies2app.data.remote.retrofit.TvList
import com.example.movies2app.data.local.roomDb.MoviesDataBase
import com.example.movies2app.data.remote.retrofit.MoviesApi
import javax.inject.Inject

class MoviesRepo @Inject constructor(
    val api: MoviesApi,
    val db: MoviesDataBase,
) {
    suspend fun getMovies(page: Int): MoviesList {
        return api.getMovies(page)

    }

    suspend fun getTv(page: Int): TvList {
        return api.getTv(page)

    }


    suspend fun getPerson(page: Int): PersonList {
        return api.getPerson(page)

    }

    suspend fun getSearch(SearchItem : String): MoviesList {
        return api.getSearch(SearchItem)

    }
    suspend fun getMoviesItems(genresId:Int,page: Int): MoviesList {

           return api.getMoviesItems(genresId,page)

    }
    suspend fun getCategories() : CategoryList {

        return api.getCategories()

    }

    val daoInstance= db.dao

}