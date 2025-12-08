package com.example.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.common.ui.theme.PokedexAppTheme
import com.example.core.common.ui.theme.Green
import com.example.model.Pokemon

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    pokemonList: List<Pokemon>,
    listState: LazyListState,
    isLoading: Boolean,
    onPokemonClicked: (Int) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(pokemonList,
            key = {pokemon -> pokemon.pokedexIndex}
        ) { pokemon ->
            /*
            * If we did
            * onPokemonClicked = { onPokemonClicked(pokemon.pokedexIndex) }
            * a new lambda instance is created, which can trigger recomposition
            * Instead, pass the index inside PokemonListItem, and create the lambda there
            * to avoid unnecessary recomposition
            * */
            PokemonListItem(
                pokemon,
                onPokemonClicked = onPokemonClicked,
                pokemonIndex = pokemon.pokedexIndex
            )
        }

        item {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun PokemonListItem(
    pokemon: Pokemon,
    onPokemonClicked: (Int) -> Unit,
    pokemonIndex: Int
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
    ) {
        /* Construct the final lambda inside its own clickable modifier.
         This makes the lambda passed from the parent more stable, and reduces recomposition
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(2.dp, Green),
                    shape = RoundedCornerShape(16.dp)

                )
                .clickable { onPokemonClicked(pokemonIndex) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val formattedString = "#${pokemon.pokedexIndex}: ${pokemon.name}"
                Text(
                    text = formattedString,
                    modifier = Modifier.padding(start = 8.dp)
                )
                AsyncImage(
                    modifier = Modifier.size(48.dp),
                    model = pokemon.spriteUrl,
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = ""
                )
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewHomeContent(){
    PokedexAppTheme {
        HomeContent(
            pokemonList = listOf(Pokemon(
                page = 0,
                nameField = "Pikachu",
                url ="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"
            )),
            listState = rememberLazyListState(),
            isLoading = false,
            onPokemonClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeLoading(){
    PokedexAppTheme {
        HomeContent(
            pokemonList = emptyList(),
            listState = rememberLazyListState(),
            isLoading = true,
            onPokemonClicked = {}
        )
    }
}