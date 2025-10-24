package com.example.network.service

import com.example.network.model.PokemonResponse
import retrofit2.Response
import javax.inject.Inject

// the service is injected into the client
class PokedexClient @Inject constructor(
    private val pokedexService: PokedexService
) { // describe the client/service relationship
    suspend fun fetchPokemonList(page: Int): Response<PokemonResponse>{
     return pokedexService.fetchPokemonList(
         limit = PAGING_SIZE,
         offset = page * PAGING_SIZE
     )
    }

    suspend fun fetchPokemonInfo(name: String){}

    // when to use a companion object?
    companion object {
        private const val PAGING_SIZE = 20
    }
}