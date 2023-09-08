@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.movies2app.userInterface

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.movies2app.component.CoilImage
import com.example.movies2app.data.constant.Constant
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.example.movies2app.viewModel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: MovieViewModel,
    entry: NavBackStackEntry
) {

    val text: MutableState<String> = remember { mutableStateOf("") }


    val search by viewModel.searchLiveData.observeAsState(emptyList())





    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, bottom = 100.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, top = 0.dp, end = 5.dp, bottom = 5.dp),
            value = text.value,
            onValueChange = {
                text.value = it
                viewModel.getSearch(text.value)
            },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            label = { Text(text = "Search here", color = Color.Black) },
        )



            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
            items(search!!) {searchLiveData->
                Card(
                    modifier = Modifier
                        .padding(5.dp).clickable {
                            entry.savedStateHandle.set("movieTitle", searchLiveData.title)
                            entry.savedStateHandle.set("moviePoster", searchLiveData.poster_path)
                            entry.savedStateHandle.set("movieVote", searchLiveData.vote_average)
                            entry.savedStateHandle.set("movieOverView", searchLiveData.overview)

                            entry.savedStateHandle.set("movieItems", searchLiveData)

                            navController.navigate("MoviesDetails")
                        },

                    elevation = CardDefaults.cardElevation(15.dp)
                ){
                    SearchItem(searchLiveData!!)
                }



            }
        }

    }

}


@Composable
fun SearchItem(movie: MoviesItems){

    Column(
        modifier = Modifier
            .fillMaxSize(),

        Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            data = Constant.imageBaseUrl + movie.poster_path,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.FillBounds,
        )
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

