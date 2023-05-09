package com.jetpack.compose.main

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val orientation = LocalConfiguration.current.orientation
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
    val isPhoneScreen : Boolean =
        (orientation == Configuration.ORIENTATION_PORTRAIT  && screenWidthDp < 600) || (orientation == Configuration.ORIENTATION_LANDSCAPE && screenHeightDp < 600)

    val maxScreenHeight = if(orientation == Configuration.ORIENTATION_LANDSCAPE) screenHeightDp*8/5 else { if(isPhoneScreen)screenHeightDp*9/10 else screenHeightDp }

    LazyColumn {
        item {
            Box(
                modifier = Modifier
                    .background(bgColor)
                    .fillMaxWidth()
                    .height(maxScreenHeight.dp)
            ) {
                // Background
                Image(
                    painter = painterResource(id = R.drawable.pokeball_silhuette),
                    contentDescription = null,
                    modifier = Modifier
                        .size(700.dp)
                        .offset(180.dp, 160.dp)
                )

                // Card View
                Card(
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = if(isPhoneScreen) 330.dp else 550.dp)
                        .height(if (isPhoneScreen) 500.dp else 800.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 80.dp, start = 30.dp, end = 30.dp,
                                bottom = if(orientation == Configuration.ORIENTATION_LANDSCAPE) 200.dp else 100.dp)
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
                                    detail.stats[it]?.base_stat.toString()
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

                // Pokemon Image
                val imageTopPadding: Dp = if (isPhoneScreen) 120.dp else 150.dp
                val imageSize : Dp = if(isPhoneScreen) 400.dp else 620.dp
                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png",
                    contentDescription = pokemon.name,
                    modifier = Modifier
                        .height(imageSize)
                        .fillMaxWidth()
                        .padding(top = imageTopPadding)
                )

                // Elements
                val typeTopPadding: Dp = if (isPhoneScreen) 80.dp else 120.dp
                LazyRow (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(start = 90.dp, top = typeTopPadding)
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
                                .width(120.dp)
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
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val orientation = LocalConfiguration.current.orientation

    val isPhoneScreen : Boolean =
        (orientation == Configuration.ORIENTATION_PORTRAIT  && screenWidthDp < 600) || (orientation == Configuration.ORIENTATION_LANDSCAPE && screenHeightDp < 600)

    val typography: TextStyle = if (isPhoneScreen){ Typography.h3 } else { Typography.h2 }

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
                    .size(50.dp)
            )
        }
        Text(
            text = pokemon.name!!.capitalize(Locale.ROOT),
            style = typography,
            textAlign = TextAlign.Start,
            color = Color.Black,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(start = 20.dp)
        )
        Text(
            text = "#${"%04d".format(pokemon.id)}",
            style = typography,
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


