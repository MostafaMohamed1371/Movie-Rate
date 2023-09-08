package com.example.movies2app.data.remote.retrofit

import com.example.movies2app.data.constant.Constant
import com.example.movies2app.domin.util.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("discover/movie?api_key=${Constant.API_KEY}")
    suspend fun getMovies(
        @Query("page") page: Int
    ): MoviesList

    @GET("discover/tv?api_key=${Constant.API_KEY}")
    suspend fun getTv(
        @Query("page") page: Int
    ): TvList


    @GET("trending/person/week?api_key=${Constant.API_KEY}")
    suspend fun getPerson(
        @Query("page") page: Int
    ): PersonList


    @GET("search/movie?api_key=${Constant.API_KEY}")
    suspend fun getSearch(@Query("query") SearchItem : String) : MoviesList


    @GET("discover/movie?api_key=${Constant.API_KEY}")
    suspend fun getMoviesItems(
        @Query("with_genres") genresId:Int,
        @Query("page") page: Int,
        @Query("language") language: String = "en"
    ): MoviesList

    @GET("genre/movie/list?api_key=${Constant.API_KEY}")
    suspend fun getCategories(
        @Query ("language") language:String = "en"
    ): CategoryList

}