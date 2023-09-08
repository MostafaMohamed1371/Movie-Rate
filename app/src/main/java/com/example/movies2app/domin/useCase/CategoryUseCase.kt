package com.example.movies2app.domin.useCase

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies2app.paging.MoviesIdPagingSource
import com.example.movies2app.data.repositry.MoviesRepo
import com.example.movies2app.data.remote.retrofit.MoviesItems
import com.example.movies2app.domin.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryUseCase  @Inject constructor (private val repository: MoviesRepo) {

    operator fun invoke(genresId:Int): Flow<Resource<Pager<Int, MoviesItems>>> = flow {
        try {
            emit(Resource.Loading<Pager<Int, MoviesItems>>())
            val getMovies = Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = {
                   MoviesIdPagingSource(repository,genresId)
                }
            )
            emit(Resource.Success<Pager<Int, MoviesItems>>(getMovies))
        }catch (e:Exception){
            emit(Resource.Error<Pager<Int, MoviesItems>>("${e.localizedMessage} : An unexpected error happened"))
        }

    }

}

