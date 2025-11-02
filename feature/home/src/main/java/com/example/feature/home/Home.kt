package com.example.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.core.common.ui.R
import com.example.core.common.ui.theme.Green
import com.example.model.Pokemon
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val pokemonList by homeViewModel.pokemonList.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.home_title)) },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Green
                )
            )
        }
    ) { paddingValues ->
            HomeContent(
                modifier = Modifier.padding(paddingValues),
                pokemonList = pokemonList,
                listState = listState
            )
    }

    LaunchedEffect(listState, pokemonList) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            if (lastVisibleItem == null || pokemonList.isEmpty()){
                false
            } else {
                lastVisibleItem.index >= (pokemonList.size - 8)
            }
        }
            .distinctUntilChanged()
            .collect { shouldFetch ->
                if (shouldFetch){
                    homeViewModel.fetchNextPokemonList()
                }
            }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    pokemonList: List<Pokemon>,
    listState: LazyListState
){

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(pokemonList) { pokemon ->
            PokemonListItem(pokemon)
        }
    }
}

@Composable
fun PokemonListItem(pokemon: Pokemon){
    Row(
        modifier = Modifier
            .padding(16.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(2.dp, Green),
                    shape = RoundedCornerShape(16.dp)

                )
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding( 8.dp)
                ,
                verticalAlignment = Alignment.CenterVertically
            ){
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