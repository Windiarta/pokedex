package com.jetpack.compose.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jetpack.compose.data.Pokemon
import com.jetpack.compose.data.PokemonRepository
import com.jetpack.compose.network.ApiConfig
import kotlinx.coroutines.flow.Flow

class MainViewModel (private val pokemonRepository: PokemonRepository): ViewModel() {
    fun getPokemons() : Flow<PagingData<Pokemon>> = pokemonRepository.getListPokemon().cachedIn(viewModelScope)
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

object Injection{
    fun provideRepository(context: Context): PokemonRepository {
        val apiService = ApiConfig.getApiService()
        return PokemonRepository(apiService)
    }
}