package com.example.movies2app.userInterface

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.movies2app.R
import com.example.movies2app.component.CoilImage
import com.example.movies2app.data.constant.Constant
import com.example.movies2app.context.MoviesApplication
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.example.movies2app.data.remote.retrofit.PersonItems
import com.example.movies2app.data.remote.retrofit.TvItems
import com.example.movies2app.viewModel.MovieViewModel
import io.github.muddz.styleabletoast.StyleableToast


@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun CartScreen(
    navController: NavHostController,
    viewModel: MovieViewModel,
    entry: NavBackStackEntry
) {

    val getMovies by viewModel.cartMovieLiveData.observeAsState(emptyList())
    val getTv by viewModel.cartTvLiveData.observeAsState(emptyList())
    val getPerson by viewModel.cartPersonLiveData.observeAsState(emptyList())



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp, top = 20.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.Start
    ) {

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
            rows = GridCells.Fixed(1),
            content = {
                items(getMovies.size) { index ->
                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                entry.savedStateHandle.set("movieTitle", getMovies[index]!!.title)
                                entry.savedStateHandle.set(
                                    "moviePoster",
                                    getMovies[index]!!.poster_path
                                )
                                entry.savedStateHandle.set(
                                    "movieVote",
                                    getMovies[index]!!.vote_average
                                )
                                entry.savedStateHandle.set(
                                    "movieOverView",
                                    getMovies[index]!!.overview
                                )
                                entry.savedStateHandle.set("movieItems", getMovies[index]!!)

                                navController.navigate("MoviesDetails")
                            },

                        elevation = CardDefaults.cardElevation(15.dp)
                    ) {
                        val dismissState = rememberDismissState()

                        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                            viewModel.deleteMovie(getMovies[index])
                        }
                        SwipeToDismiss(
                            state = dismissState,
                            modifier = Modifier
                                .padding(vertical = Dp(1f)),
                            directions = setOf(
                                DismissDirection.EndToStart
                            ),

                            background = {
                                val color by animateColorAsState(
                                    when (dismissState.targetValue) {
                                        DismissValue.Default -> Color.White
                                        else -> Color.Red
                                    }
                                )
                                val alignment = Alignment.CenterEnd
                                val icon = Icons.Default.Delete

                                val scale by animateFloatAsState(
                                    if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                                )

                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = Dp(20f)),
                                    contentAlignment = alignment
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = "Delete Icon",
                                        modifier = Modifier.scale(scale)
                                    )
                                }
                            },
                            dismissContent = {
                                CartMovieItems(getMovies[index],viewModel)

                            }
                        )

                    }


                }
            },
            userScrollEnabled = true
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
                items(getTv.size) { index ->
                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                entry.savedStateHandle.set("movieTitle", getTv[index]!!.name)
                                entry.savedStateHandle.set(
                                    "moviePoster",
                                    getTv[index]!!.poster_path
                                )
                                entry.savedStateHandle.set("movieVote", getTv[index]!!.vote_average)
                                entry.savedStateHandle.set("movieOverView", getTv[index]!!.overview)

                                entry.savedStateHandle.set("tvItems", getTv[index]!!)

                                navController.navigate("TvDetails")
                            },

                        elevation = CardDefaults.cardElevation(15.dp)
                    ) {

                        val dismissState = rememberDismissState()

                        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                            viewModel.deleteTv(getTv[index])
                        }
                        SwipeToDismiss(
                            state = dismissState,
                            modifier = Modifier
                                .padding(vertical = Dp(1f)),
                            directions = setOf(
                                DismissDirection.EndToStart
                            ),

                            background = {
                                val color by animateColorAsState(
                                    when (dismissState.targetValue) {
                                        DismissValue.Default -> Color.White
                                        else -> Color.Red
                                    }
                                )
                                val alignment = Alignment.CenterEnd
                                val icon = Icons.Default.Delete

                                val scale by animateFloatAsState(
                                    if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                                )

                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = Dp(20f)),
                                    contentAlignment = alignment
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = "Delete Icon",
                                        modifier = Modifier.scale(scale)
                                    )
                                }
                            },
                            dismissContent = {
                                CartTvItems(getTv[index]!!,viewModel)

                            }
                        )


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
                items(getPerson.size) { index ->
                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                entry.savedStateHandle.set("movieTitle", getPerson[index]!!.name)
                                entry.savedStateHandle.set(
                                    "moviePoster",
                                    getPerson[index]!!.profile_path
                                )
                                entry.savedStateHandle.set(
                                    "movieVote",
                                    getPerson[index]!!.vote_average
                                )
//                                entry.savedStateHandle.set("movieOverView", getPerson[index]!!.overview)

                                entry.savedStateHandle.set("personItems", getPerson[index]!!)

                                navController.navigate("PersonDetails")
                            },

                        elevation = CardDefaults.cardElevation(15.dp)
                    ) {


                        val dismissState = rememberDismissState()

                        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                            viewModel.deletePerson(getPerson[index])
                        }
                        SwipeToDismiss(
                            state = dismissState,
                            modifier = Modifier
                                .padding(vertical = Dp(1f)),
                            directions = setOf(
                                DismissDirection.EndToStart
                            ),

                            background = {
                                val color by animateColorAsState(
                                    when (dismissState.targetValue) {
                                        DismissValue.Default -> Color.White
                                        else -> Color.Red
                                    }
                                )
                                val alignment = Alignment.CenterEnd
                                val icon = Icons.Default.Delete

                                val scale by animateFloatAsState(
                                    if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                                )

                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = Dp(20f)),
                                    contentAlignment = alignment
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = "Delete Icon",
                                        modifier = Modifier.scale(scale)
                                    )
                                }
                            },
                            dismissContent = {
                                CartPersonItems(getPerson[index]!!,viewModel)

                            }
                        )

                    }
                }
            },
            userScrollEnabled = true
        )


    }
}




@Composable
fun CartMovieItems(movie: MoviesItems, viewModel: MovieViewModel){
    var openAlertDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight(),
        Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            CoilImage(
                data = Constant.imageBaseUrl + movie.poster_path,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillBounds,
            )

            IconButton(onClick = {
                openAlertDialog=true

            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    tint = Color.Black
                )
            }
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
    if (openAlertDialog){
        AlertDialogExample(
            onDismissRequest = {
                 openAlertDialog=false
                               },
            onConfirmation = {
                viewModel.deleteMovie(movie)
                openAlertDialog=false
                StyleableToast.makeText(MoviesApplication.getApplicationContext(),"Sucess Delete Movie",R.style.exampleToast).show()
            },
            dialogTitle = "Delete movie" ,
            dialogText = "this item is delete",
            icon = Icons.Default.Info
        )
    }



}

@Composable
fun CartTvItems(tv: TvItems, viewModel: MovieViewModel){
    var openAlertDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight(),
        Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            CoilImage(
                data = Constant.imageBaseUrl + tv.poster_path,
                contentDescription = tv.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillBounds,
            )


            IconButton(onClick = {
                openAlertDialog=true

            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    tint = Color.Black
                )
            }
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

    if (openAlertDialog){
        AlertDialogExample(
            onDismissRequest = {
                openAlertDialog=false
            },
            onConfirmation = {
                viewModel.deleteTv(tv)
                openAlertDialog=false
                StyleableToast.makeText(MoviesApplication.getApplicationContext(),"Sucess Delete Tv",R.style.exampleToast).show()
            },
            dialogTitle = "Delete tv" ,
            dialogText = "this item is delete",
            icon = Icons.Default.Info
        )
    }


}

@Composable
fun CartPersonItems(person: PersonItems, viewModel: MovieViewModel) {
    var openAlertDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight(),
        Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            CoilImage(
                data = Constant.imageBaseUrl + person.profile_path,
                contentDescription = person.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillBounds,
            )

            IconButton(onClick = {
                openAlertDialog=true
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    tint = Color.Black
                )
            }
        }
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

    if (openAlertDialog){
        AlertDialogExample(
            onDismissRequest = {
                openAlertDialog=false
            },
            onConfirmation = {
                viewModel.deletePerson(person)
                openAlertDialog=false
                StyleableToast.makeText(MoviesApplication.getApplicationContext(),"Sucess Delete Person",R.style.exampleToast).show()
            },
            dialogTitle = "Delete person" ,
            dialogText = "this item is delete",
            icon = Icons.Default.Info
        )
    }

}


@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}


