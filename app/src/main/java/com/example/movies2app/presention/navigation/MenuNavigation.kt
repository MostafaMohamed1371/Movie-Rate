package com.example.movies2app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuNavigation(val route:String,val title:String,val icon:ImageVector){

    object Home:MenuNavigation(
        route = "Home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Cart:MenuNavigation(
        route = "Cart" ,
        title = "Cart",
        icon = Icons.Default.ShoppingCart
    )
    object Search:MenuNavigation(
        route = "Search",
        title = "Search",
        icon = Icons.Default.Search
    )
    object Setting:MenuNavigation(
        route = "Setting",
        title = "Setting",
        icon = Icons.Default.Settings
    )

}
