package com.example.movies2app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.movies2app.navigation.BottomNavigationApp
import com.example.movies2app.ui.theme.Movies2AppTheme
import com.example.movies2app.viewModel.CategoryViewModel
import com.example.movies2app.viewModel.MovieViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var mAuth: FirebaseAuth
    @Inject
    lateinit var reference: DatabaseReference
    @Inject
    @Named("userId")
    lateinit var userId:String
//    @Inject
//    @Named("imageStorage")
//    lateinit var imageStorage: StorageReference

    private val viewModel by viewModels<MovieViewModel>()
    private val viewModelId by viewModels<CategoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            Movies2AppTheme {
//                val vm: MovieViewModel = hiltViewModel()
//                val moviesCategory=vm.movies.collectAsLazyPagingItems()
                BottomNavigationApp(mAuth,reference, userId,viewModel,viewModelId)
            }
        }
    }
}

