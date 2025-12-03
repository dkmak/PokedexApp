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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    val homeRepository: HomeRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)

    // you can add this to the UI, maybe as a snackbar or toast
    val error: StateFlow<String?> = _error.asStateFlow()

    private val pokemonFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonList: StateFlow<List<Pokemon>> = pokemonFetchingIndex.flatMapLatest { page ->
        homeRepository.fetchPokemonList(page = page)
            .onStart {
                _isLoading.value = true
            }.map { result ->
                result.getOrThrow()
            }.catch { throwable ->
                _error.value = throwable.message ?: "an unknown error occurred"
                emit(pokemonList.value)
            }
            .onCompletion { _isLoading.value = false }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMER.toLong()),
        initialValue = emptyList()
    )

    // without relying on a changing fetching index
    val pokemonList2 = homeRepository.fetchPokemonList(pokemonFetchingIndex.value)
        .onStart { _isLoading.value = true }
        .map { result ->
            result.getOrThrow()
        }.catch { throwable ->
            _error.value = throwable.message ?: "error found retrieving pokemon list"
            emit(pokemonList.value)
        }.onCompletion { _isLoading.value = false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMER.toLong()),
            initialValue = emptyList()
        )


    // if we were to trigger this directly from the API
    fun fetchPokemonList(){
        pokemonFetchingIndex.value++
        homeRepository.fetchPokemonList(pokemonFetchingIndex.value)
            .onStart {
                _isLoading.value = true
            }
            .map { result -> result.getOrThrow() }
            .catch { throwable ->
                _error.value = throwable.message ?: "error found retrieving pokemon list"
                emit(pokemonList.value)
            }
            .onCompletion { _isLoading.value = true }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMER.toLong()),
                initialValue = emptyList<List<Pokemon>>()
            )
    }


    fun fetchNextPokemonList() {
        pokemonFetchingIndex.value++
    }

    companion object {
        const val TIMER = 5_000
    }
}

// implement later
sealed interface HomeUIState {
    data object Loading : HomeUIState
    data class Error(val message: String) : HomeUIState
    data object Idle : HomeUIState
}