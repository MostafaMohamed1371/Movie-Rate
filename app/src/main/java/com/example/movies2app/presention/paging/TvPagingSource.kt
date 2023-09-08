package com.example.movies2app.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies2app.data.repositry.MoviesRepo
import com.example.movies2app.data.remote.retrofit.TvItems

class TvPagingSource (
    private val repositry: MoviesRepo
): PagingSource<Int, TvItems>() {
    override fun getRefreshKey(state: PagingState<Int, TvItems>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvItems>
    {
        return try {
            val pageNumber = params.key ?: 1
            val tvCategory = repositry.getTv(page = pageNumber).results

            Log.d("TvViewModel", pageNumber.toString())

            LoadResult.Page(
                data = tvCategory,
                prevKey = if(pageNumber ==1)null else pageNumber.minus(1),
                nextKey = if(tvCategory.isEmpty()) null else pageNumber.plus(1)
            )

        }catch (e: Exception) {
            Log.d("TvViewModel", e.message.toString())
            LoadResult.Error(e)

        }


    }
}