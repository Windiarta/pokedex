package com.jetpack.compose.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.jetpack.compose.R
import com.jetpack.compose.data.Pokemon
import java.util.*

@Composable
fun Pokeball(degrees: Int) {
    Image(
        painter = painterResource(id = R.drawable.pokeball),
        contentDescription = null,
        modifier = Modifier
            .fillMaxHeight()
            .offset(x = (-410).dp)
            .clip(CircleShape)
            .rotate(degrees.toFloat())
    )
}

@Composable
fun ListScreen(pokemons: LazyPagingItems<Pokemon>, navigateToDetail: (String) -> Unit, navigateToAbout: () -> Unit) {
    val listState = rememberLazyListState()
    val rotation by remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navigateToAbout()},
                content = {Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "about_page", modifier = Modifier.size(80.dp), tint = Color.White)},
                backgroundColor = Color.Black,
                modifier = Modifier
                    .size(100.dp)
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ){
        Box(
            modifier = Modifier.padding(it).fillMaxSize()
        ){
            Pokeball(degrees = rotation - 1)
            LazyColumn(
                state = listState,
                modifier = Modifier.padding(350.dp, 20.dp, 10.dp, 20.dp)
            ) {
                itemsIndexed(
                    items = pokemons
                ) { index, pokemon ->
                    Row(modifier = Modifier.clickable {
                        navigateToDetail((index + 1).toString())
                    }) {
                        AsyncImage(
                            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${index + 1}.png",
                            contentDescription = pokemon?.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(150.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = "${pokemon?.name}".capitalize(Locale.ROOT),
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 16.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Divider()
                }
            }
        }

    }

}
