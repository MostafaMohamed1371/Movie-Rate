package com.example.movies2app.data.type

import android.os.Bundle
import androidx.navigation.NavType
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.example.movies2app.data.remote.retrofit.TvItems
import com.google.gson.Gson

class TvItemsType : NavType<TvItems>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): TvItems? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): TvItems {
        return Gson().fromJson(value, TvItems::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: TvItems) {
        bundle.putParcelable(key, value)
    }
}