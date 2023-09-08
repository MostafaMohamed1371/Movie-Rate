package com.example.movies2app.data.remote.retrofit

data class MoviesList(
    val page: Int,
    val results: List<MoviesItems>,
    val total_pages: Int,
    val total_results: Int
)
