package com.example.movies2app.userInterface

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies2app.R
import com.example.movies2app.component.CoilImage
import com.example.movies2app.data.constant.Constant
import com.example.movies2app.context.MoviesApplication
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.example.movies2app.data.remote.retrofit.PersonItems
import com.example.movies2app.data.remote.retrofit.TvItems
import com.example.movies2app.viewModel.CategoryViewModel
import com.example.movies2app.viewModel.MovieViewModel
import io.github.muddz.styleabletoast.StyleableToast
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@SuppressLint("SuspiciousIndentation")
@Composable
fun MoviesDetails(
    navController: NavHostController,
    viewModel: MovieViewModel,
    entry: NavBackStackEntry
) {

    val movieTitle= navController.previousBackStackEntry?.savedStateHandle?.get<String>("movieTitle")
    val moviePoster= navController.previousBackStackEntry?.savedStateHandle?.get<String>("moviePoster")
    val movieVote= navController.previousBackStackEntry?.savedStateHandle?.get<Double>("movieVote")
    val movieOverView= navController.previousBackStackEntry?.savedStateHandle?.get<String>("movieOverView")
    val moviesItem= navController.previousBackStackEntry?.savedStateHandle?.get<MoviesItems>("movieItems")
    val moviesItems = viewModel.movies.collectAsLazyPagingItems()





    var isFavourite by rememberSaveable() { mutableStateOf(false) }
    val icon= if (isFavourite){
        Icons.Filled.Favorite
    }else{
        Icons.Filled.FavoriteBorder
    }




    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),

        ) {
        CoilImage(
            data = Constant.imageBaseUrl + moviePoster,
            contentDescription = movieTitle.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.sdp),
            contentScale = ContentScale.FillBounds,
        )
        Row {
            Text(
                text = movieTitle.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 15.ssp,
                color = Color(0xFF000000),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .width(200.sdp)
                    .padding(10.sdp),
                maxLines = 2,

                )

            Image(
                painter = painterResource(id = R.drawable.baseline_star_24),
                modifier = Modifier
                    .width(25.sdp)
                    .height(50.sdp)
                    .padding(bottom = 10.sdp, start = 5.sdp), contentDescription = "star"
            )

            Text(
                text = movieVote.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 15.ssp,
                color = Color(0xFF000000),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .width(40.sdp)
                    .padding(top = 10.sdp),


                )

            Image(
                imageVector = icon,
                contentDescription = "Favourite",
                modifier = Modifier
                    .width(30.sdp)
                    .height(40.sdp)
                    .padding(top = 5.sdp)
                    .clickable {
                        isFavourite = !isFavourite
                        if (isFavourite){
                            viewModel.upinsertMovies(moviesItem!!)
                            StyleableToast.makeText(MoviesApplication.getApplicationContext(),"Sucess Add Movie",R.style.exampleToast).show()

                        }

                    }
            )
        }

        Text(
            text = movieOverView.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.sdp),


            )


        Text(
            text = "More like this",
            fontWeight = FontWeight.Bold,
            fontSize = 15.ssp,
            color = Color(0xFF000000),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .width(200.sdp)
                .padding(10.sdp),
            maxLines = 1,

            )

        LazyHorizontalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(top = 10.dp, start = 5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            rows = GridCells.Fixed(1),
            content = {
                items(moviesItems.itemCount) { index ->
                    Card(
                        modifier = Modifier
                            .padding(5.dp).clickable {
                                entry.savedStateHandle.set("movieTitle", moviesItems[index]!!.title)
                                entry.savedStateHandle.set("moviePoster", moviesItems[index]!!.poster_path)
                                entry.savedStateHandle.set("movieVote", moviesItems[index]!!.vote_average)
                                entry.savedStateHandle.set("movieOverView", moviesItems[index]!!.overview)

                                entry.savedStateHandle.set("movieItems", moviesItems[index]!!)

                                navController.navigate("MoviesDetails")
//                                            {
//                                                popUpTo(navController.graph.findStartDestination().id) {
//                                                    saveState = true
//                                                }
//
//                                                launchSingleTop = true
//                                                restoreState = true
//                                            }

                            },

                        elevation = CardDefaults.cardElevation(15.dp)
                    ) {

                        MovieItem(moviesItems[index]!!)
                    }
                }
            }
        )



    }




}


@Composable
fun TvDetails(
    navController: NavHostController,
    viewModel: MovieViewModel,
    entry: NavBackStackEntry
) {

    val movieTitle= navController.previousBackStackEntry?.savedStateHandle?.get<String>("movieTitle")
    val moviePoster= navController.previousBackStackEntry?.savedStateHandle?.get<String>("moviePoster")
    val movieVote= navController.previousBackStackEntry?.savedStateHandle?.get<Double>("movieVote")
    val movieOverView= navController.previousBackStackEntry?.savedStateHandle?.get<String>("movieOverView")
    val tvItem= navController.previousBackStackEntry?.savedStateHandle?.get<TvItems>("tvItems")
    val tvItems = viewModel.tv.collectAsLazyPagingItems()




    var isFavourite by rememberSaveable() { mutableStateOf(false) }
    val icon= if (isFavourite){
        Icons.Filled.Favorite
    }else{
        Icons.Filled.FavoriteBorder
    }




        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),

            ) {
            CoilImage(
                data = Constant.imageBaseUrl + moviePoster ,
                contentDescription = movieTitle.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.sdp),
                contentScale = ContentScale.FillBounds,
            )
            Row {
                Text(
                    text =movieTitle.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.ssp,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(200.sdp)
                        .padding(10.sdp),
                    maxLines = 1,

                    )

                Image(
                    painter = painterResource(id = R.drawable.baseline_star_24),
                    modifier = Modifier
                        .width(25.sdp)
                        .height(50.sdp)
                        .padding(bottom = 10.sdp, start = 5.sdp), contentDescription = "star"
                )

                Text(
                    text = movieVote.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.ssp,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(40.sdp)
                        .padding(top = 10.sdp),


                    )

                Image(
                    imageVector = icon,
                    contentDescription = "Favourite",
                    modifier = Modifier
                        .width(30.sdp)
                        .height(40.sdp)
                        .padding(top = 5.sdp)
                        .clickable {
                            isFavourite = !isFavourite
                            if (isFavourite){
                                viewModel.upinsertTv(tvItem!!)
                                StyleableToast.makeText(MoviesApplication.getApplicationContext(),"Sucess Add Tv",R.style.exampleToast).show()

                            }

                        }
                )
            }

            Text(
                text = movieOverView.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 12.ssp,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.sdp),




                )

            Text(
                text = "More like this",
                fontWeight = FontWeight.Bold,
                fontSize = 15.ssp,
                color = Color(0xFF000000),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .width(200.sdp)
                    .padding(10.sdp),
                maxLines = 1,

                )


            LazyHorizontalGrid(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(top = 10.dp, start = 5.dp),
                rows = GridCells.Fixed(1),
                content = {
                    items(tvItems.itemCount) { index ->
                        Card(
                            modifier = Modifier
                                .padding(5.dp).clickable {
                                    entry.savedStateHandle.set("movieTitle", tvItems[index]!!.name)
                                    entry.savedStateHandle.set("moviePoster", tvItems[index]!!.poster_path)
                                    entry.savedStateHandle.set("movieVote", tvItems[index]!!.vote_average)
                                    entry.savedStateHandle.set("movieOverView", tvItems[index]!!.overview)

                                    entry.savedStateHandle.set("tvItems", tvItems[index]!!)

                                    navController.navigate("TvDetails")
                                },

                            elevation = CardDefaults.cardElevation(15.dp)
                        ) {

                            TvItem(tvItems[index]!!)
                        }
                    }
                },
                userScrollEnabled = true
            )



        }





}


@Composable
fun PersonDetails(
    navController: NavHostController,
    viewModel: MovieViewModel,
    entry: NavBackStackEntry
) {

    val movieTitle= navController.previousBackStackEntry?.savedStateHandle?.get<String>("movieTitle")
    val moviePoster= navController.previousBackStackEntry?.savedStateHandle?.get<String>("moviePoster")
    val movieVote= navController.previousBackStackEntry?.savedStateHandle?.get<Double>("movieVote")
    val personItem= navController.previousBackStackEntry?.savedStateHandle?.get<PersonItems>("personItems")
    val personItems = viewModel.person.collectAsLazyPagingItems()






    var isFavourite by rememberSaveable() { mutableStateOf(false) }
    val icon= if (isFavourite){
        Icons.Filled.Favorite
    }else{
        Icons.Filled.FavoriteBorder
    }


        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),

            ) {
            CoilImage(
                data = Constant.imageBaseUrl + moviePoster ,
                contentDescription = movieTitle.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.sdp),
                contentScale = ContentScale.FillBounds,
            )
            Row {
                Text(
                    text =movieTitle.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.ssp,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(200.sdp)
                        .padding(10.sdp),
                    maxLines = 1,

                    )

                Image(
                    painter = painterResource(id = R.drawable.baseline_star_24),
                    modifier = Modifier
                        .width(25.sdp)
                        .height(50.sdp)
                        .padding(bottom = 10.sdp, start = 5.sdp), contentDescription = "star"
                )

                Text(
                    text = movieVote.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.ssp,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .width(40.sdp)
                        .padding(top = 10.sdp),


                    )

                Image(
                    imageVector = icon,
                    contentDescription = "Favourite",
                    modifier = Modifier
                        .width(30.sdp)
                        .height(40.sdp)
                        .padding(top = 5.sdp)
                        .clickable {
                            isFavourite = !isFavourite
                            if (isFavourite){
                                viewModel.upinsertPerson(personItem!!)
                                StyleableToast.makeText(MoviesApplication.getApplicationContext(),"Sucess Add Person",R.style.exampleToast).show()

                            }

                        }
                )
            }

            Text(
                text = "Null",
                fontWeight = FontWeight.Bold,
                fontSize = 12.ssp,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.sdp),




                )


            Text(
                text = "More like this",
                fontWeight = FontWeight.Bold,
                fontSize = 15.ssp,
                color = Color(0xFF000000),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .width(200.sdp)
                    .padding(10.sdp),
                maxLines = 1,

                )



            LazyHorizontalGrid(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(top = 10.dp, start = 5.dp),
                rows = GridCells.Fixed(1),
                content = {
                    items(personItems.itemCount) { index ->
                        Card(
                            modifier = Modifier
                                .padding(5.dp).clickable {
                                    entry.savedStateHandle.set("movieTitle", personItems[index]!!.name)
                                    entry.savedStateHandle.set("moviePoster", personItems[index]!!.profile_path)
                                    entry.savedStateHandle.set("movieVote", personItems[index]!!.vote_average)
//                                            entry.savedStateHandle.set("movieOverView", personItems[index]!!.overview)

                                    entry.savedStateHandle.set("personItems", personItems[index]!!)

                                    navController.navigate("PersonDetails")
                                },

                            elevation = CardDefaults.cardElevation(15.dp)
                        ) {

                            PersonItem(personItems[index]!!)
                        }
                    }
                },
                userScrollEnabled = true
            )



        }



}