package com.jetpack.compose.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jetpack.compose.data.DetailResponse
import com.jetpack.compose.data.Pokemon
import com.jetpack.compose.data.PokemonDetailResponse
import com.jetpack.compose.data.PokemonRepository
import com.jetpack.compose.network.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    fun getPokemons(): Flow<PagingData<Pokemon>> =
        pokemonRepository.getListPokemon().cachedIn(viewModelScope)

    private val _pokemonDetail = MutableStateFlow<PokemonDetailResponse?>(null)
    val pokemonDetail: StateFlow<PokemonDetailResponse?> = _pokemonDetail

    private val _pokemonMoreDetail = MutableStateFlow<DetailResponse?>(null)
    val pokemonMoreDetail: StateFlow<DetailResponse?> = _pokemonMoreDetail

    fun getPokemonDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = pokemonRepository.getPokemonDetail(id)
                _pokemonDetail.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching pokemon detail: ${e.message}")
            }
        }
    }

    fun getPokemonMoreDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = pokemonRepository.getPokemonMoreDetail(id)
                _pokemonMoreDetail.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching pokemon detail: ${e.message}")
            }
        }
    }
}

class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

object Injection {
    fun provideRepository(context: Context): PokemonRepository {
        val apiService = ApiConfig.getApiService()
        return PokemonRepository(apiService)
    }
}