package com.example.core.database.entity.mapper

import com.example.core.database.entity.PokemonEntity
import com.example.model.Pokemon

/**
 * Converts a domain model [Pokemon] to a database [PokemonEntity].
 */
fun Pokemon.asEntity(): PokemonEntity {
    return PokemonEntity(
        pokedexIndex = this.pokedexIndex,
        page = this.page,
        name = this.nameField,
        url = this.url
    )
}

/**
 * Converts a database [PokemonEntity] to a domain model [Pokemon].
 */
fun PokemonEntity.asDomain(): Pokemon {
    return Pokemon(
        page = this.page,
        nameField = this.name,
        url = this.url
    )
}

/**
 * Converts a list of domain models to a list of database entities.
 */
fun List<Pokemon>.asEntity(): List<PokemonEntity> {
    return this.map { it.asEntity() }
}

/**
 * Converts a list of database entities to a list of domain models.
 * Handles null lists gracefully by converting them to an empty list.
 */
fun List<PokemonEntity>?.asDomain(): List<Pokemon> {
    return this.orEmpty().map { it.asDomain() }
}
