package com.example.core.data.repository.home

import android.util.Log
import com.example.core.database.dao.PokemonDao
import com.example.core.database.entity.mapper.asDomain
import com.example.core.database.entity.mapper.asEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.model.Pokemon
import com.example.network.service.PokedexClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.collections.emptyList

class HomeRepositoryImpl @Inject constructor(
    private val pokedexClient: PokedexClient,
    private val pokemonDao: PokemonDao
    // inject the dispatcher
): HomeRepository {
    override fun fetchPokemonList(page: Int): Flow<List<Pokemon>> = flow {
        var pagedPokemonList = pokemonDao.getPokemonList(page).asDomain()

        if (pagedPokemonList.isEmpty()){
            try {
                val response = pokedexClient.fetchPokemonList(page = page)
                Log.d("HomeRepository", "fetchPokemonList: ${response}")

                pagedPokemonList = response.results
                pagedPokemonList.forEach{ pokemon ->
                    pokemon.page = page
                }
                pokemonDao.insertPokemonList(pagedPokemonList.asEntity())

                emit(pokemonDao.getAllPokemonList(page).asDomain())
            } catch (e: Exception){

                Log.d("HomeRepository", "fetchPokemonList: ${e.message}")
                emit(emptyList())
            }
        } else {
            emit(pokemonDao.getAllPokemonList(page).asDomain())
        }
    }.flowOn(Dispatchers.IO)
}
