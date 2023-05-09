package com.jetpack.compose.main

import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.jetpack.compose.R
import com.jetpack.compose.data.Pokemon
import java.util.*

@Composable
fun Pokeball(degrees: Int) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        Image(
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .offset(x = (-screenWidthDp * 3 / 5).dp)
                .rotate(degrees.toFloat())
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .offset(x = (-screenHeightDp/5).dp)
                .rotate(degrees.toFloat())
        )
    }

}

@Composable
fun ListScreen(
    pokemons: LazyPagingItems<Pokemon>,
    navigateToDetail: (String) -> Unit,
    navigateToAbout: () -> Unit
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val orientation = LocalConfiguration.current.orientation
    val listState = rememberLazyListState()
    val rotation by remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToAbout() },
                content = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "about_page",
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                },
                backgroundColor = Color.Black,
                modifier = Modifier
                    .size(60.dp)
            )

        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Pokeball(degrees = rotation - 1)
            val lazyPaddingModifier: Dp = if(orientation == Configuration.ORIENTATION_LANDSCAPE){
                (screenHeightDp*8/10).dp
            } else {
                (screenWidthDp / 3).dp
            }
            LazyColumn(
                state = listState,
                modifier = Modifier.padding(start = lazyPaddingModifier, end = 10.dp)
            ) {
                itemsIndexed(
                    items = pokemons
                ) { index, pokemon ->
                    Row(modifier = Modifier.clickable {
                        navigateToDetail((index + 1).toString())
                    }) {
                        val imgSize: Dp = if (screenWidthDp > 600) {
                            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                                (screenWidthDp / 5).dp
                            } else {
                                (screenHeightDp / 5).dp
                            }
                        } else {
                            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                                (screenWidthDp / 4).dp
                            } else {
                                (screenHeightDp / 4).dp
                            }
                        }
                        val theme = if (screenWidthDp > 600){MaterialTheme.typography.h3} else MaterialTheme.typography.h4
                        AsyncImage(
                            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${index + 1}.png",
                            contentDescription = pokemon?.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(imgSize)
                                .clip(CircleShape)
                        )
                        Text(
                            text = "${pokemon?.name}".capitalize(Locale.ROOT),
                            fontWeight = FontWeight.Medium,
                            style = theme,
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
