package com.example.core.data.repository.home

import kotlinx.coroutines.flow.Flow
import com.example.model.Pokemon

interface HomeRepository {
    fun fetchPokemonList(page: Int): Flow<Result<List<Pokemon>>>
}