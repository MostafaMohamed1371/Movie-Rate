package com.example.movies2app.userInterface

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies2app.R
import com.example.movies2app.component.Chip
import com.example.movies2app.component.CoilImage
import com.example.movies2app.context.MoviesApplication
import com.example.movies2app.data.constant.Constant
import com.example.movies2app.data.remote.retrofit.CategoryItems
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.example.movies2app.data.remote.retrofit.PersonItems
import com.example.movies2app.data.remote.retrofit.TvItems
import com.example.movies2app.navigation.MenuNavigation
import com.example.movies2app.viewModel.CategoryViewModel
import com.example.movies2app.viewModel.MovieViewModel
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Context
import ir.kaaveh.sdpcompose.sdp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: MovieViewModel,
    entry: NavBackStackEntry,
    viewModelId: CategoryViewModel,
    mAuth: FirebaseAuth
) {
    val moviesItems = viewModel.movies.collectAsLazyPagingItems()
    val tvItems = viewModel.tv.collectAsLazyPagingItems()
    val personItems = viewModel.person.collectAsLazyPagingItems()
    val categoryMovie = viewModelId.categoryLiveData.observeAsState(emptyList()).value


    LaunchedEffect(Unit){
        viewModelId.getCategory()
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Scaffold(
           
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(), Alignment.Center
                        ) {

                            Text(text = "Movies App", color = Color.Black)

                        }
                    },
                    // in start page
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "menue",
                                tint = Color.Black
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(18.dp)
                        .clip(shape = RoundedCornerShape(15.dp)),
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = colorResource(
                            id = R.color.ColorAmericanPurple
                        )
                    ),
                    actions = {
                        IconButton(onClick = { navController.navigate(MenuNavigation.Search.route) }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Black
                            )
                        }
                        IconButton(onClick = {
                            if (mAuth.currentUser!=null){
                                mAuth.signOut()

                                Auth.GoogleSignInApi
                                navController.navigate("Splash"){
                                    popUpTo(MenuNavigation.Home.route){
                                        inclusive=true
                                    }
                                }

                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = "Logout",
                                tint = Color.Black
                            )
                        }
                    }


                )
            },
            containerColor = colorResource(id = R.color.ColorGraniteGray),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp, top = 90.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.Start
                ) {



                    LazyRow {
                        items(categoryMovie.size) { genre ->
                            Chip(
                                genre = categoryMovie[genre],
                                selected = viewModelId.selectedCategry.value == categoryMovie[genre],
                                onSelected = {
                                            if (viewModelId.selectedCategry.value==categoryMovie[genre])

                                                viewModelId.setSelectedCategory(CategoryItems())
                                            else

                                                viewModelId.setSelectedCategory(categoryMovie[genre])

                                },
                                modifier = Modifier
                            )
                        }
                    }

                    if (viewModelId.selectedCategry.value.name.isNotEmpty()){
                        viewModelId.getMoviesId(viewModelId.selectedCategry.value.id)
                        MovieList(navController,viewModelId,entry)
                    }
                    else{


                        Text(
                            text = "Movies",

                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = colorResource(id = R.color.black)
                            ),
                            modifier = Modifier
                                .padding(start = 15.dp, top = 30.dp)


                        )

                        LazyHorizontalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .padding(top = 20.dp, start = 5.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalArrangement = Arrangement.Center,
                            rows = GridCells.Fixed(1),
                            content = {
                                items(moviesItems.itemCount) { index ->
                                    Card(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .clickable {
                                                entry.savedStateHandle.set(
                                                    "movieTitle",
                                                    moviesItems[index]!!.title
                                                )
                                                entry.savedStateHandle.set(
                                                    "moviePoster",
                                                    moviesItems[index]!!.poster_path
                                                )
                                                entry.savedStateHandle.set(
                                                    "movieVote",
                                                    moviesItems[index]!!.vote_average
                                                )
                                                entry.savedStateHandle.set(
                                                    "movieOverView",
                                                    moviesItems[index]!!.overview
                                                )

                                                entry.savedStateHandle.set(
                                                    "movieItems",
                                                    moviesItems[index]!!
                                                )

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

                        Text(
                            text = "Tv",

                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = colorResource(id = R.color.black)
                            ),
                            modifier = Modifier.padding(start = 15.dp, top = 30.dp)

                        )

                        LazyHorizontalGrid(

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .padding(top = 20.dp, start = 5.dp),
                            rows = GridCells.Fixed(1),
                            content = {
                                items(tvItems.itemCount) { index ->
                                    Card(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .clickable {
                                                entry.savedStateHandle.set(
                                                    "movieTitle",
                                                    tvItems[index]!!.name
                                                )
                                                entry.savedStateHandle.set(
                                                    "moviePoster",
                                                    tvItems[index]!!.poster_path
                                                )
                                                entry.savedStateHandle.set(
                                                    "movieVote",
                                                    tvItems[index]!!.vote_average
                                                )
                                                entry.savedStateHandle.set(
                                                    "movieOverView",
                                                    tvItems[index]!!.overview
                                                )

                                                entry.savedStateHandle.set(
                                                    "tvItems",
                                                    tvItems[index]!!
                                                )

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


                        Text(
                            text = "Person",

                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = colorResource(id = R.color.black)
                            ),
                            modifier = Modifier.padding(start = 15.dp, top = 30.dp)

                        )

                        LazyHorizontalGrid(

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .padding(top = 20.dp, start = 5.dp),
                            rows = GridCells.Fixed(1),
                            content = {
                                items(personItems.itemCount) { index ->
                                    Card(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .clickable {
                                                entry.savedStateHandle.set(
                                                    "movieTitle",
                                                    personItems[index]!!.name
                                                )
                                                entry.savedStateHandle.set(
                                                    "moviePoster",
                                                    personItems[index]!!.profile_path
                                                )
                                                entry.savedStateHandle.set(
                                                    "movieVote",
                                                    personItems[index]!!.vote_average
                                                )
//                                            entry.savedStateHandle.set("movieOverView", personItems[index]!!.overview)

                                                entry.savedStateHandle.set(
                                                    "personItems",
                                                    personItems[index]!!
                                                )

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
            }


        )




    }
}








@Composable
fun MovieItem(movie: MoviesItems) {

    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight(),
        Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
         Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart
           ){
            CoilImage(
                data = Constant.imageBaseUrl + movie.poster_path,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillBounds,
            )

             Text(
                 text = movie.vote_average.toString(),
                 fontWeight = FontWeight.Bold,
                 fontSize = 12.sp,
                 color = Color(0xFF000000),
                 textAlign = TextAlign.Center,
                 modifier = Modifier
                     .width(40.sdp)
                     .padding(10.dp)
                     .background(Color.LightGray, shape = RectangleShape),


             )

        }

        Text(
            text = movie.title,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )
    }
}


@Composable
fun TvItem(tv: TvItems) {

    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight(),
        Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            CoilImage(
                data = Constant.imageBaseUrl + tv.poster_path,
                contentDescription = tv.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillBounds,
            )

            Text(
                text = tv.vote_average.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(40.sdp)
                    .padding(10.dp)
                    .background(Color.LightGray, shape = RectangleShape),


                )
        }
        Text(
            text = tv.name,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )
    }
}



@Composable
fun PersonItem(person: PersonItems) {

    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight(),
        Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            data = Constant.imageBaseUrl + person.profile_path,
            contentDescription = person.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.FillBounds,
        )
        Text(
            text = person.name,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun MovieList(
    navController: NavHostController,
    viewModel: CategoryViewModel,
    entry: NavBackStackEntry
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(530.dp)) {



        val movies = viewModel.movies.value.movies!!.flow.collectAsLazyPagingItems()


        LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                content = {
                    items(movies.itemCount) { index ->
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    entry.savedStateHandle.set(
                                        "movieTitle",
                                        movies[index]!!.title
                                    )
                                    entry.savedStateHandle.set(
                                        "moviePoster",
                                        movies[index]!!.poster_path
                                    )
                                    entry.savedStateHandle.set(
                                        "movieVote",
                                        movies[index]!!.vote_average
                                    )
                                    entry.savedStateHandle.set(
                                        "movieOverView",
                                        movies[index]!!.overview
                                    )

                                    entry.savedStateHandle.set(
                                        "movieItems",
                                        movies[index]!!
                                    )

                                    navController.navigate("MoviesDetails")


                                },
                            elevation = CardDefaults.cardElevation(15.dp),
                        ) {

                            MovieItem(movies[index]!!)
                        }
                    }
                }
            )




    }
}