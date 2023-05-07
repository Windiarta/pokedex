package com.jetpack.compose.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jetpack.compose.network.ApiService
import kotlinx.coroutines.flow.Flow

class PokemonRepository (private val apiService: ApiService) {
    fun getListPokemon(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                PokemonPagingSource(apiService)
            }
        ).flow
    }
}