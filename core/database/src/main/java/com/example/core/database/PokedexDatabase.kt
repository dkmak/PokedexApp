package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.dao.PokemonDao
import com.example.core.database.entity.PokemonEntity


@Database(
    entities = [PokemonEntity::class],
    version = 4,
    exportSchema = true
)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}