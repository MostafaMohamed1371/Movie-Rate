package com.example.movies2app.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class DataLogin(
    var userName:String?=null,
    var email:String?=null,
    var password:String?=null,

)
