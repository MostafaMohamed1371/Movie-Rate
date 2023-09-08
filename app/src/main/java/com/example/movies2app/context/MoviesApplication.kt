package com.example.movies2app.context
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication: Application(){
    init {
        application=this
    }
    companion object{
        private lateinit var application:MoviesApplication
        fun getApplicationContext(): Context =application.applicationContext
    }
}