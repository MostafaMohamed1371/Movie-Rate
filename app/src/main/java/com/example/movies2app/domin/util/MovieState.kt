package com.example.movies2app.domin.util

import androidx.paging.Pager
import com.example.movies2app.data.remote.retrofit.MoviesItems

data class MovieState (
    val isLoading: Boolean = false,
    val movies : Pager<Int, MoviesItems>? = null,
    val error: String = ""
)
