package com.example.movies2app.userInterface

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.movies2app.R
import com.example.movies2app.navigation.MenuNavigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, mAuth: FirebaseAuth) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }
            ))
        delay(2000L)
        if (mAuth.currentUser!=null){
            navController.navigate(MenuNavigation.Home.route){
                popUpTo("Splash") {
                    inclusive=true
                    saveState = true

                }
                launchSingleTop = true
                restoreState = true

            }
        }else{
            navController.navigate("Login"){
                popUpTo("Splash") {
                    inclusive=true
                    saveState = true

                }
                launchSingleTop = true
                restoreState = true

            }
        }

    })

        Column(Modifier.fillMaxSize().background(Color.White)){

            Image(painter = painterResource(id = R.drawable.cinema),
                contentDescription = "movie icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )

        }


}