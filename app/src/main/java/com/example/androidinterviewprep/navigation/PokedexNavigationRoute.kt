package com.example.pokedexapp.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.core.common.ui.navigation.POKEMON_ID_ARG

/*
class PokedexNavigationRoute {
}*/
sealed interface PokedexNavigationRoute {
    val route: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    data object Home : PokedexNavigationRoute {
        override val route = "home"
    }
    data object Profile : PokedexNavigationRoute {
        override val route = "profile/{$POKEMON_ID_ARG}"
        override val arguments: List<NamedNavArgument>
            = listOf(navArgument(POKEMON_ID_ARG) {type = NavType.IntType})

        fun createRoute(pokemonId: Int) = "profile/$pokemonId"
    }
}
