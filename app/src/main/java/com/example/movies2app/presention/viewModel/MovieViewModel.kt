package com.example.movies2app.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies2app.paging.MoviesPagingSource
import com.example.movies2app.paging.PersonPagingSource
import com.example.movies2app.paging.TvPagingSource
import com.example.movies2app.data.repositry.MoviesRepo
import com.example.movies2app.data.remote.retrofit.CategoryItems
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.example.movies2app.data.remote.retrofit.PersonItems
import com.example.movies2app.data.remote.retrofit.TvItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor (
    private val repositry: MoviesRepo,
) : ViewModel() {
    private val moviesPagingSource: MoviesPagingSource = MoviesPagingSource(repositry)
       val movies:Flow<PagingData<MoviesItems>> = Pager(
           pagingSourceFactory = {
               moviesPagingSource
           },
           config = PagingConfig(pageSize = 10)
       ).flow.cachedIn(viewModelScope)




    private val tvPagingSource: TvPagingSource = TvPagingSource(repositry)
    val tv:Flow<PagingData<TvItems>> = Pager(
        pagingSourceFactory = {
            tvPagingSource
        },
        config = PagingConfig(pageSize = 10)
    ).flow.cachedIn(viewModelScope)


    private val personPagingSource: PersonPagingSource = PersonPagingSource(repositry)
    val person:Flow<PagingData<PersonItems>> = Pager(
        pagingSourceFactory = {
            personPagingSource
        },
        config = PagingConfig(pageSize = 10)
    ).flow.cachedIn(viewModelScope)



    private var _searchLiveData = MutableLiveData<List<MoviesItems>>()
    val searchLiveData = _searchLiveData




    fun getSearch(SearchItem : String) {
        viewModelScope.launch {
            try {
                val search = repositry.getSearch(SearchItem).results
                _searchLiveData.value = search



            } catch (e: Exception) {
                Log.d("SearchViewModel", e.message.toString())

            }
        }
    }


    fun upinsertMovies(moviesItem: MoviesItems){
        viewModelScope.launch {
           repositry.daoInstance.upsertMovie(moviesItem)
        }
    }

    fun deleteMovie(moviesItem: MoviesItems){
        viewModelScope.launch {
            repositry.daoInstance.deleteMovie(moviesItem)
        }
    }

    private  var _cartMovieLiveData= repositry.daoInstance.getAllMovies()
    val cartMovieLiveData: LiveData<List<MoviesItems>> = _cartMovieLiveData





    fun upinsertTv(tvItem: TvItems){
        viewModelScope.launch {
            repositry.daoInstance.upsertTv(tvItem)
        }
    }

    fun deleteTv(tvItem: TvItems){
        viewModelScope.launch {
            repositry.daoInstance.deleteTv(tvItem)
        }
    }

    private  var _cartTvLiveData= repositry.daoInstance.getAllTv()
    val cartTvLiveData: LiveData<List<TvItems>> = _cartTvLiveData






    fun upinsertPerson(personItem: PersonItems){
        viewModelScope.launch {
            repositry.daoInstance.upsertPerson(personItem)
        }
    }

    fun deletePerson(personItem: PersonItems){
        viewModelScope.launch {
            repositry.daoInstance.deletePerson(personItem)
        }
    }

    private  var _cartPersonLiveData= repositry.daoInstance.getAllPerson()
    val cartPersonLiveData: LiveData<List<PersonItems>> = _cartPersonLiveData





}