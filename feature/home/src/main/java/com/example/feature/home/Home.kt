package com.example.feature.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.common.ui.R
import com.example.core.common.ui.theme.Green
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onPokemonClicked: (Int) -> Unit
) {

    val pokemonList by homeViewModel.pokemonList.collectAsStateWithLifecycle()
    val isLoading by homeViewModel.isLoading.collectAsStateWithLifecycle()
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
            listState = listState,
            isLoading = isLoading,
            onPokemonClicked = onPokemonClicked
        )
    }

    LaunchedEffect(listState, pokemonList) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            if (lastVisibleItem == null || pokemonList.isEmpty()) {
                false
            } else {
                lastVisibleItem.index >= (pokemonList.size - POKEMON_LIST_BUFFER)
            }
        }
            .distinctUntilChanged()
            .collect { shouldFetch ->
                if (shouldFetch) {
                    homeViewModel.fetchNextPokemonList()
                }
            }
    }
}

const val POKEMON_LIST_BUFFER = 8