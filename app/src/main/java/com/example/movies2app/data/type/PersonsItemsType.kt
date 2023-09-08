package com.example.movies2app.data.type

import android.os.Bundle
import androidx.navigation.NavType
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.example.movies2app.data.remote.retrofit.PersonItems
import com.google.gson.Gson

class PersonsItemsType : NavType<PersonItems>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PersonItems? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): PersonItems {
        return Gson().fromJson(value, PersonItems::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: PersonItems) {
        bundle.putParcelable(key, value)
    }
}