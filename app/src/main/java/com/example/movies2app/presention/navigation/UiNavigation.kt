package com.example.movies2app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movies2app.userInterface.CartScreen
import com.example.movies2app.userInterface.HomeScreen
import com.example.movies2app.userInterface.LoginScreen
import com.example.movies2app.userInterface.MoviesDetails
import com.example.movies2app.userInterface.PersonDetails
import com.example.movies2app.userInterface.RegisterScreen
import com.example.movies2app.userInterface.SearchScreen
import com.example.movies2app.userInterface.SettingScreen
import com.example.movies2app.userInterface.SplashScreen
import com.example.movies2app.userInterface.TvDetails
import com.example.movies2app.viewModel.CategoryViewModel
import com.example.movies2app.viewModel.MovieViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference


@Composable
fun NavScreen(
    navController: NavHostController,
    mAuth: FirebaseAuth,
    reference: DatabaseReference,
    userId: String,
    viewModel: MovieViewModel,
    viewModelId: CategoryViewModel,
){

    NavHost(navController = navController, startDestination = "Splash")
        {
            composable(route="Splash"){
                SplashScreen(navController,mAuth)

            }

        composable("Login"){
            LoginScreen(navController,mAuth)
        }
        composable("Register"){
            RegisterScreen(navController,mAuth)
        }

            composable(MenuNavigation.Home.route) {entry->
                HomeScreen(navController, viewModel,entry,viewModelId,mAuth)
            }



        composable(MenuNavigation.Cart.route){entry->
            CartScreen(navController,viewModel,entry)
        }
        composable(MenuNavigation.Search.route){entry->
            SearchScreen(navController,viewModel,entry)
        }
        composable(MenuNavigation.Setting.route){
            SettingScreen(reference,userId)
        }



            composable(route="MoviesDetails"){entry->
                MoviesDetails(navController,viewModel,entry)
            }

            composable(route="TvDetails"){entry->
                TvDetails(navController,viewModel,entry)

            }

            composable(route="PersonDetails"){entry->
                PersonDetails(navController,viewModel,entry)

            }



       }
    }




    

