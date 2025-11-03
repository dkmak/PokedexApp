package com.example.feature.profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.common.ui.R
import com.example.core.common.ui.theme.Green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
){
    val pokemonInfo by profileViewModel.pokemonInfo.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.profile_title) + "#"+pokemonInfo.pokedexIndex + " " +pokemonInfo.name ) },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Green
                ),
                navigationIcon = {
                    BackIcon(onBackClicked = onBackClicked)
                }
            )
        }
    ) { paddingValues ->
        paddingValues
    }
}

@Composable
fun BackIcon(onBackClicked: () -> Unit) {
    IconButton(onClick = onBackClicked) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back)
        )
    }
}