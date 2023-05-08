package com.jetpack.compose.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jetpack.compose.data.Pokemon
import com.jetpack.compose.ui.theme.JetpackcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel =
            ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]

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
    val navController = rememberNavController()
    val pokemons: LazyPagingItems<Pokemon> = viewModel.getPokemons().collectAsLazyPagingItems()
    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable(route = "list") {
            ListScreen(pokemons, { pokeIndex ->
                navController.navigate("detail/$pokeIndex")
            }, {
                navController.navigate("about")
            })
        }
        composable(
            route = "detail/{pokeIndex}",
            arguments = listOf(
                navArgument("pokeIndex") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            DetailScreen(
                id = backStackEntry.arguments?.getInt("pokeIndex"),
                viewModel = viewModel
            ) { navController.navigateUp() }
        }
        composable(
            route = "about",
        ){
            AboutScreen { navController.navigateUp() }
        }
    }
}



