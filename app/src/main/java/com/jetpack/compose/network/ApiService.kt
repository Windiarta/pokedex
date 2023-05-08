package com.jetpack.compose.network

import com.jetpack.compose.BuildConfig
import com.jetpack.compose.data.DetailResponse
import com.jetpack.compose.data.PokemonDetailResponse
import com.jetpack.compose.data.PokemonResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
interface ApiService {
    @GET("v2/pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") size: Int
    ) : PokemonResponse

    @GET("v2/pokemon-species/{id}")
    fun getPokemonDetail(@Path("id") id: Int) : Call<PokemonDetailResponse>

    @GET("v2/pokemon/{id}")
    fun getPokemonMoreDetail(@Path("id") id: Int) : Call<DetailResponse>
}