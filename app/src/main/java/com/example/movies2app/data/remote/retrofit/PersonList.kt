package com.example.movies2app.data.remote.retrofit

data class PersonList(
    val page: Int,
    val results: List<PersonItems>,
    val total_pages: Int,
    val total_results: Int
)