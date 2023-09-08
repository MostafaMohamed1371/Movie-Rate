package com.example.movies2app.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies2app.data.repositry.MoviesRepo
import com.example.movies2app.data.remote.retrofit.MoviesItems

class MoviesIdPagingSource (
    private val repositry: MoviesRepo,
    val genresId:Int,
): PagingSource<Int, MoviesItems>() {
    override fun getRefreshKey(state: PagingState<Int, MoviesItems>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesItems>
    {
        return try {
            val pageNumber = params.key ?: 1
            val moviesCategory = repositry.getMoviesItems(genresId = genresId, page = pageNumber).results

            Log.d("MoviesViewModel", pageNumber.toString())

            LoadResult.Page(
                data = moviesCategory,
                prevKey = if(pageNumber ==1)null else pageNumber.minus(1),
                nextKey = if(moviesCategory.isEmpty()) null else pageNumber.plus(1)
            )

        }catch (e: Exception) {
            Log.d("MoviesViewModel", e.message.toString())
            LoadResult.Error(e)

        }


    }
}