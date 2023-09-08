package com.example.movies2app.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies2app.paging.MoviesIdPagingSource
import com.example.movies2app.data.repositry.MoviesRepo
import com.example.movies2app.data.remote.retrofit.CategoryItems
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.example.movies2app.domin.useCase.CategoryUseCase
import com.example.movies2app.domin.util.MovieState
import com.example.movies2app.domin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor (
    private val repositry: MoviesRepo,
    val categoryUseCase: CategoryUseCase
) : ViewModel() {


    private var _categoryLiveData = MutableLiveData<List<CategoryItems>>()
    val categoryLiveData = _categoryLiveData

    private var _selectedCategory = mutableStateOf(CategoryItems())

    val selectedCategry=_selectedCategory

    fun setSelectedCategory(selectedCategory: CategoryItems){
        _selectedCategory.value = selectedCategory
        _selectedCategory.value = _selectedCategory.value
    }


    fun getCategory() {
        viewModelScope.launch {
            try {
                val category = repositry.getCategories().genres
                _categoryLiveData.value = category



            } catch (e: Exception) {
                Log.d("CategoryViewModel", e.message.toString())

            }
        }
    }


    private val _state = mutableStateOf(MovieState())

    val movies: State<MovieState>
        get() = _state

    fun getMoviesId( genresId: Int) {
        categoryUseCase(genresId).onEach { result ->
            when (result) {
                is Resource.Success<*> -> {

                    _state.value = MovieState(
                        movies = result.data
                    )

                }
                is Resource.Error<*> -> {
                    _state.value = MovieState(
                        error = result.message ?: "An unexpected error happened"
                    )
                }
                is Resource.Loading<*> -> {
                    _state.value = MovieState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)


    }



}