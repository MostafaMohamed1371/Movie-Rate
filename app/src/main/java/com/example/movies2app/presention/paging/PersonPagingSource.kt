package com.example.movies2app.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies2app.data.repositry.MoviesRepo
import com.example.movies2app.data.remote.retrofit.PersonItems
import com.example.movies2app.data.remote.retrofit.TvItems

class PersonPagingSource (
    private val repositry: MoviesRepo
): PagingSource<Int, PersonItems>() {
    override fun getRefreshKey(state: PagingState<Int, PersonItems>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PersonItems>
    {
        return try {
            val pageNumber = params.key ?: 1
            val personCategory = repositry.getPerson(page = pageNumber).results

            Log.d("PersonViewModel", pageNumber.toString())

            LoadResult.Page(
                data = personCategory,
                prevKey = if(pageNumber ==1)null else pageNumber.minus(1),
                nextKey = if(personCategory.isEmpty()) null else pageNumber.plus(1)
            )

        }catch (e: Exception) {
            Log.d("PersonViewModel", e.message.toString())
            LoadResult.Error(e)

        }


    }
}