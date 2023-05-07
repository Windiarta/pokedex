package com.jetpack.compose.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.jetpack.compose.R
import com.jetpack.compose.data.Pokemon
import com.jetpack.compose.ui.theme.JetpackcomposeTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel =
            ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]

        Log.e("MAIN ACTIVITY", viewModel.getPokemons().toString())
        setContent {
            JetpackcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val pokemons: LazyPagingItems<Pokemon> = viewModel.getPokemons().collectAsLazyPagingItems()
    ItemList(pokemons)
}

@Composable
fun Pokeball(degrees: Int) {
    Image(
        painter = painterResource(id = R.drawable.pokeball),
        contentDescription = null,
        modifier = Modifier
            .size(500.dp)
            .offset(x = (-410).dp, y = 0.dp)
            .clip(CircleShape)
            .rotate(degrees.toFloat())
    )
}

@Composable
fun ItemList(pokemons: LazyPagingItems<Pokemon>) {
    val listState = rememberLazyListState()
    val rotation by remember {
        derivedStateOf{listState.firstVisibleItemIndex}
    }
    Pokeball(degrees = rotation - 1)
    LazyColumn (
        state = listState,
        modifier = Modifier.padding(350.dp, 20.dp, 10.dp, 20.dp)
    ){
        items(
            items = pokemons,
            key = { pokemon -> pokemon.name }
        ) { pokemon ->
            val id = pokemon?.url?.split("/")?.get(6)?.toInt() ?: 1
            Row {
                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(180.dp)
                        .clip(CircleShape)
                )

                Text(
                    text = "${pokemon?.name}".replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    },
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 16.dp)
                        .align(CenterVertically)
                )
            }
            Divider()
        }

    }

}

