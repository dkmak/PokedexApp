package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM PokemonEntity WHERE page = :page_")
    suspend fun getPokemonList(page_: Int): List<PokemonEntity>

    @Query("SELECT * FROM PokemonEntity WHERE page <= :page_ ORDER BY pokedexIndex ASC")
    suspend fun getAllPokemonList(page_: Int): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM PokemonEntity WHERE pokedexIndex = :id" )
    suspend fun getPokemonById(id: Int): PokemonEntity
}