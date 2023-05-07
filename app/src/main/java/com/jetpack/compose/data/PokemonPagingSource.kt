package com.jetpack.compose.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jetpack.compose.network.ApiService
import kotlinx.coroutines.delay
import java.net.SocketTimeoutException

class PokemonPagingSource(private val apiService: ApiService) : PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getPokemonList(page, params.loadSize)
            LoadResult.Page(
                data = responseData.results ?: emptyList(),
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - params.loadSize,
                nextKey = if (responseData.results.isNullOrEmpty()) null else page + params.loadSize
            )
        } catch (exception: Exception) {
            Log.e(TAG, exception.toString())
            if (exception is SocketTimeoutException) {
                delay(5000) // 5 seconds delay
                return load(params)
            }
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        private const val TAG = "PokemonPagingSource"
        const val INITIAL_PAGE_INDEX = 0
    }
}