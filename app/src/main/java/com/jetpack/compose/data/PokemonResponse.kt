package com.jetpack.compose.data

data class PokemonResponse(
	val next: String? = null,
	val previous: Any? = null,
	val count: Int? = null,
	val results: List<Pokemon>? = null
)

data class Pokemon(
	val name: String,
	val url: String
)


