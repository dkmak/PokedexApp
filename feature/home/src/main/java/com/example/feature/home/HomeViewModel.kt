package com.example.feature.home

import androidx.lifecycle.ViewModel
import com.example.core.data.repository.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.model.Pokemon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted

@HiltViewModel
class HomeViewModel @Inject constructor(
    val homeRepository: HomeRepository
): ViewModel(){
    private val pokemonFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonList : StateFlow<List<Pokemon>> = pokemonFetchingIndex.flatMapLatest { page ->
        homeRepository.fetchPokemonList(page = page)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000), // what is this
        initialValue = emptyList(),
    )

    fun fetchNextPokemonList() {
        pokemonFetchingIndex.value++
    }
}