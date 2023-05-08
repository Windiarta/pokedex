package com.jetpack.compose.data

data class PokemonDetailResponse(
	val evolutionChain: EvolutionChain? = null,
	val genera: List<GeneraItem?>? = null,
	val habitat: Habitat? = null,
	val color: Color? = null,
	val eggGroups: List<EggGroupsItem?>? = null,
	val capture_rate: Int? = null,
	val pokedexNumbers: List<PokedexNumbersItem?>? = null,
	val formsSwitchable: Boolean? = null,
	val growthRate: GrowthRate? = null,
	val flavorTextEntries: List<FlavorTextEntriesItem?>? = null,
	val id: Int? = null,
	val isBaby: Boolean? = null,
	val order: Int? = null,
	val generation: Generation? = null,
	val isLegendary: Boolean? = null,
	val palParkEncounters: List<PalParkEncountersItem?>? = null,
	val shape: Shape? = null,
	val isMythical: Boolean? = null,
	val baseHappiness: Int? = null,
	val names: List<NamesItem?>? = null,
	val varieties: List<VarietiesItem?>? = null,
	val genderRate: Int? = null,
	val name: String? = null,
	val hasGenderDifferences: Boolean? = null,
	val hatchCounter: Int? = null,
	val formDescriptions: List<Any?>? = null,
	val evolves_from_species: EvolvesFromSpecies? = null
)

data class EvolutionChain(
	val url: String? = null
)

data class Generation(
	val name: String? = null,
	val url: String? = null
)

data class PokedexNumbersItem(
	val entryNumber: Int? = null,
	val pokedex: Pokedex? = null
)

data class Version(
	val name: String? = null,
	val url: String? = null
)

data class GrowthRate(
	val name: String? = null,
	val url: String? = null
)

data class NamesItem(
	val name: String? = null,
	val language: Language? = null
)

data class FlavorTextEntriesItem(
	val language: Language? = null,
	val version: Version? = null,
	val flavorText: String? = null
)

data class EggGroupsItem(
	val name: String? = null,
	val url: String? = null
)

data class PalParkEncountersItem(
	val area: Area? = null,
	val baseScore: Int? = null,
	val rate: Int? = null
)

data class EvolvesFromSpecies(
	val name: String? = null,
	val url: String? = null
)

data class Color(
	val name: String? = null,
	val url: String? = null
)

data class Language(
	val name: String? = null,
	val url: String? = null
)

data class Habitat(
	val name: String? = null,
	val url: String? = null
)

data class Area(
	val name: String? = null,
	val url: String? = null
)

data class GeneraItem(
	val genus: String? = null,
	val language: Language? = null
)

data class VarietiesItem(
	val pokemon: Pokemon? = null,
	val isDefault: Boolean? = null
)


data class Pokedex(
	val name: String? = null,
	val url: String? = null
)

data class Shape(
	val name: String? = null,
	val url: String? = null
)

