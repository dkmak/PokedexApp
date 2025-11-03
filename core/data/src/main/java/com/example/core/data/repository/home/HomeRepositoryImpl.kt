package com.example.core.data.repository.home

import android.util.Log
import com.example.core.database.dao.PokemonDao
import com.example.core.database.entity.mapper.asDomain
import com.example.core.database.entity.mapper.asEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.model.Pokemon
import com.example.network.service.PokedexClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import kotlin.collections.emptyList

class HomeRepositoryImpl @Inject constructor(
    private val pokedexClient: PokedexClient,
    private val pokemonDao: PokemonDao,
    private val ioDispatcher: CoroutineDispatcher
) : HomeRepository {
    override fun fetchPokemonList(page: Int): Flow<List<Pokemon>> = flow {
        try {
            val response = pokedexClient.fetchPokemonList(page = page)
            val networkPokemon = response.results.map { it.copy(page = page) }
            
            pokemonDao.insertPokemonList(networkPokemon.asEntity())

        } catch (e: Exception) {
            Log.d("HomeRepository", "Network fetch for page $page failed: ${e.message}")
        }

        emit(pokemonDao.getAllPokemonList(page).asDomain())
    }.flowOn(ioDispatcher)
}
