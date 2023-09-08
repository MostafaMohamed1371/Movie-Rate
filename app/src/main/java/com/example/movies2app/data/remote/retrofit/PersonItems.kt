package com.example.movies2app.data.remote.retrofit

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "person")
data class PersonItems(
    val adult: Boolean,
    val gender: Int,
    @PrimaryKey
    val id: Int,
    val known_for_department: String,
    val media_type: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String,
    val vote_average: Double
): Parcelable