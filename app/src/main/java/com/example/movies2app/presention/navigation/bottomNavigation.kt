package com.example.movies2app.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movies2app.R
import com.example.movies2app.viewModel.CategoryViewModel
import com.example.movies2app.viewModel.MovieViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationApp(
    mAuth: FirebaseAuth,
    reference: DatabaseReference,
    userId: String,
    viewModel: MovieViewModel,
    viewModelId: CategoryViewModel,
){


    val navController= rememberNavController()
    val items= listOf(MenuNavigation.Home,MenuNavigation.Cart,MenuNavigation.Search,MenuNavigation.Setting,)
    val naBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute=naBackStackEntry?.destination?.route
    val showBottomBar = navController
        .currentBackStackEntryAsState().value?.destination?.route in items.map { it.route }


        Scaffold(

            bottomBar = {

                if (showBottomBar){
                NavigationBar(
                    modifier = Modifier
                        .padding(18.dp)
                        .clip(shape = RoundedCornerShape(15.dp)),
                    containerColor = colorResource(id = R.color.ColorAmericanPurple),
                ) {
                    items.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true

                                }
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = Color.Black
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.title,
                                    modifier = Modifier.height(25.dp),
                                    tint = Color.Black
                                )

                            },


                            )
                    }

                  //شكل من اشكال ال bottom Navigation

                  /*
                    NavigationBarItem(
                        selected = bottomState == "Cart",
                        onClick = { bottomState = "Cart" },
                        label = {
                            Text(text = "Cart")
                        },
                        icon = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                            }
                        }

                    )
                    NavigationBarItem(
                        selected = bottomState == "Search",
                        onClick = { bottomState = "Search" },
                        label = {
                            Text(text = "Search")
                        },
                        icon = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = "menue")
                            }
                        }

                    )
                    NavigationBarItem(
                        selected = bottomState == "Setting",
                        onClick = { bottomState = "Setting" },
                        label = {
                            Text(text = "Setting")
                        },
                        icon = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Settings, contentDescription = "menue")
                            }
                        }

                    )

*/

                }


            }

            },
            containerColor = colorResource(id = R.color.ColorGraniteGray)

        ) {
//            Box(modifier = Modifier
//                .fillMaxSize(),
//                Alignment.Center){
//                Text(text = bottomState, fontSize = 25.sp, fontWeight = FontWeight.Bold)
//            }

            NavScreen(navController,mAuth,reference, userId,viewModel,viewModelId)

        }
    }









