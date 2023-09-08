package com.example.movies2app.data.type

import android.os.Bundle
import androidx.navigation.NavType
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.google.gson.Gson

class MoviesItemsType : NavType<MoviesItems>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): MoviesItems? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): MoviesItems {
        return Gson().fromJson(value, MoviesItems::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: MoviesItems) {
        bundle.putParcelable(key, value)
    }
}