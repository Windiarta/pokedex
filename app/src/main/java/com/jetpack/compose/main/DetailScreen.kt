package com.jetpack.compose.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jetpack.compose.R
import com.jetpack.compose.data.DetailResponse
import com.jetpack.compose.data.PokemonDetailResponse
import com.jetpack.compose.ui.theme.*
import java.util.*

@Composable
fun DetailScreen(id: Int?, viewModel: MainViewModel, navigateBack: () -> Unit) {
    val pokemonDetail by viewModel.pokemonDetail.collectAsState(initial = null)
    val pokemonMoreDetail by viewModel.pokemonMoreDetail.collectAsState(initial = null)

    if (id != null) {
        viewModel.getPokemonDetail(id)
        viewModel.getPokemonMoreDetail(id)
    }

    pokemonDetail?.let { pokemon ->
        pokemonMoreDetail?.let { detail ->
            DesignPokemon(pokemon = pokemon, detail = detail)
            TopBar(navigateBack, detail)
        }
    } ?: run {
        LoadingScreen()
    }
}

@Composable
fun DesignPokemon(pokemon: PokemonDetailResponse, detail : DetailResponse) {
    val bgColor = when (pokemon.color?.name) {
        "black" -> {
            Black
        }
        "blue" -> {
            Blue
        }
        "brown" -> {
            Brown
        }
        "gray" -> {
            Gray
        }
        "green" -> {
            Green
        }
        "pink" -> {
            Pink
        }
        "purple" -> {
            Purple
        }
        "red" -> {
            Red
        }
        "white" -> {
            White
        }
        "yellow" -> {
            Yellow
        }
        else -> {
            White
        }
    }
    LazyColumn {
        item {
            Box(
                modifier = Modifier
                    .background(bgColor)
                    .fillMaxWidth()
                    .height(1260.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pokeball_silhuette),
                    contentDescription = null,
                    modifier = Modifier
                        .size(700.dp)
                        .offset(180.dp, 160.dp)
                )
                Card(
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 600.dp)
                        .height(800.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 100.dp, start = 30.dp, end = 30.dp, bottom = 150.dp)
                    ) {
                        // Basic Information
                        item {
                            Text(text = "Basic Information", style = Typography.h3)
                            Text(
                                text = "Height: " + (detail.height!! * 10).toString() + " cm",
                                style = Typography.h5
                            )
                            Text(
                                text = "Weight: " + (detail.weight!! * 100).toString() + " grams",
                                style = Typography.h5
                            )
                            Text(
                                text = "Habitat: " + (pokemon.habitat?.name
                                    ?: "Area Unknown").capitalize(Locale.ROOT),
                                style = Typography.h5
                            )
                            Text(
                                text = "Evolve From: " + (pokemon.evolves_from_species?.name
                                    ?: "None").capitalize(Locale.ROOT), style = Typography.h5
                            )
                            Text(
                                text = "Capture Rate: " + pokemon.capture_rate.toString(),
                                style = Typography.h5
                            )
                        }

                        // Stats
                        item {
                            Divider(Modifier.padding(top = 20.dp, bottom = 50.dp))
                            Text(text = "Stats", style = Typography.h3)
                        }
                        items(count = 6) {
                            Text(
                                text = "${detail.stats?.get(it)?.stat?.name!!.capitalize(Locale.ROOT)}: ${
                                    detail.stats.get(
                                        it
                                    )?.base_stat.toString()
                                }", style = Typography.h5
                            )
                        }

                        // Abilities
                        item {
                            Divider(Modifier.padding(top = 20.dp, bottom = 50.dp))
                            Text(text = "Ability", style = Typography.h3)
                        }
                        items(count = detail.abilities?.size!!) {
                            Text(
                                text = "$it. ${detail.abilities[it]?.ability?.name?.capitalize()}",
                                style = Typography.h5
                            )
                        }

                        // Moves
                        item {
                            Divider(Modifier.padding(top = 20.dp, bottom = 50.dp))
                            Text(text = "Moves", style = Typography.h3)
                        }
                        items(count = detail.moves?.size!!) {
                            Text(
                                text = "$it. ${detail.moves[it]?.move?.name?.capitalize()}",
                                style = Typography.h5
                            )
                        }
                        item { Divider(modifier = Modifier.padding(top = 50.dp)) }
                    }
                }

                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png",
                    contentDescription = pokemon.name,
                    modifier = Modifier
                        .height(700.dp)
                        .fillMaxWidth()
                        .padding(top = 150.dp)
                )
                LazyRow (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(start = 100.dp, top = 120.dp)
                ){
                    val types = detail.types ?: emptyList()
                    items(types.size) { index ->
                        val color = when (types[index]?.type?.name?.lowercase(Locale.getDefault())) {
                            "normal" -> Gray
                            "fire" -> Red
                            "water" -> Blue
                            "grass" -> Green
                            "electric" -> Yellow
                            "ice" -> LightBlue
                            "fighting" -> Brown
                            "poison" -> Purple
                            "ground" -> Brown
                            "flying" -> Blue
                            "psychic" -> Pink
                            "bug" -> Green
                            "rock" -> Gray
                            "ghost" -> Purple
                            "dragon" -> Red
                            "dark" -> DarkGray
                            "steel" -> Gray
                            "fairy" -> Pink
                            else -> Black
                        }
                        Card(
                            backgroundColor = color,
                            shape = Shapes.medium,
                            border = BorderStroke(2.dp, color = Black),
                            modifier = Modifier
                                .width(170.dp)
                                .height(35.dp)
                        ) {
                            Text(
                                text = types[index]?.type?.name!!.uppercase(),
                                textAlign = TextAlign.Center,
                                style = Typography.h5
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(navigateBack: () -> Unit, pokemon: DetailResponse) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 30.dp, end = 20.dp)
    ){
        IconButton(onClick = navigateBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(70.dp)
            )
        }
        Text(
            text = pokemon.name!!.capitalize(Locale.ROOT),
            style = Typography.h2,
            textAlign = TextAlign.Start,
            color = Color.Black,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        )
        Text(
            text = "#${"%04d".format(pokemon.id)}",
            style = Typography.h4,
            color = Color.Black,
            textAlign = TextAlign.End,
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}


