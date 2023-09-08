package com.example.movies2app.data.remote.retrofit

data class TvList(
    val page: Int,
    val results: List<TvItems>,
    val total_pages: Int,
    val total_results: Int
)