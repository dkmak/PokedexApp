package com.example.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.common.ui.R
import com.example.core.common.ui.theme.Green
import com.example.model.Pokemon
import kotlinx.coroutines.flow.compose
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val pokemonList by homeViewModel.pokemonList.collectAsStateWithLifecycle()

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
                pokemonList = pokemonList
            ) {
                homeViewModel.fetchNextPokemonList()
            }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    pokemonList: List<Pokemon>,
    onFetchNextPokemonList: () -> Unit
){

    val listState = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(pokemonList) { pokemon ->
            PokemonListItem(pokemon)
        }
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
                    onFetchNextPokemonList()
                }
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
            Text(
                text = pokemon.name,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

}