package com.jetpack.compose.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jetpack.compose.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PokemonRepository(private val apiService: ApiService) {
    fun getListPokemon(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                PokemonPagingSource(apiService)
            }
        ).flow
    }

    suspend fun getPokemonDetail(id: Int): PokemonDetailResponse {
        return withContext(Dispatchers.IO) {
            val client = apiService.getPokemonDetail(id)
            val response = client.execute()
            if (response.isSuccessful) {
                response.body()
                    ?: throw IllegalStateException("Pokemon detail response body is null")
            } else {
                throw HttpException(response)
            }
        }
    }

    suspend fun getPokemonMoreDetail(id: Int): DetailResponse {
        return withContext(Dispatchers.IO) {
            val client = apiService.getPokemonMoreDetail(id)
            val response = client.execute()
            if (response.isSuccessful) {
                response.body()
                    ?: throw IllegalStateException("Pokemon detail response body is null")
            } else {
                throw HttpException(response)
            }
        }
    }


    companion object {
        private const val TAG = "PokemonRepository"
    }
}