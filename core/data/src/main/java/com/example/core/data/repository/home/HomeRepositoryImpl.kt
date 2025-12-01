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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.Dispatcher
import kotlin.collections.emptyList

class HomeRepositoryImpl @Inject constructor(
    private val pokedexClient: PokedexClient,
    private val pokemonDao: PokemonDao,
    private val ioDispatcher: CoroutineDispatcher
) : HomeRepository {
    override fun fetchPokemonList(page: Int): Flow<Result<List<Pokemon>>> = flow {
        val response = pokedexClient.fetchPokemonList(page = page)

        val networkPokemon = response.results.map { it.copy(page = page) }
        pokemonDao.insertPokemonList(networkPokemon.asEntity())

        val cachedPokemon = pokemonDao.getAllPokemonList(page).asDomain()
        emit(cachedPokemon)

    }.map { pokemonList ->
        Result.success(pokemonList)
    }.catch { throwable ->
        // could also be a database failure
        Log.d("HomeRepository", "fetch for page $page failed: ${throwable.message}")

        val cachedPokemon = pokemonDao.getAllPokemonList(page).asDomain()
        // I could also emit a failure and bubble that up
        emit(Result.success(cachedPokemon))
    }.flowOn(ioDispatcher)

}