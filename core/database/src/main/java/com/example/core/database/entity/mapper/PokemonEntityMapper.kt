package com.example.core.database.entity.mapper

import com.example.core.database.entity.PokemonEntity
import com.example.model.Pokemon

interface EntityMapper<Entity, Domain> {
    fun asEntity(domain: Domain): Entity
    fun asDomain(entity: Entity): Domain
}

object PokemonEntityMapper : EntityMapper<List<PokemonEntity>, List<Pokemon>> {
    override fun asEntity(domain: List<Pokemon>): List<PokemonEntity> {
        return domain.map { pokemon ->
            PokemonEntity(
                page = pokemon.page,
                name = pokemon.name,
                url = pokemon.url
            )
        }
    }

    override fun asDomain(entity: List<PokemonEntity>): List<Pokemon> {
        return entity.map{ entity ->
            Pokemon(
                page = entity.page,
                name = entity.name,
                url = entity.url
            )
        }
    }
}

fun List<Pokemon>.asEntity(): List<PokemonEntity> {
    return PokemonEntityMapper.asEntity(this)
}

fun List<PokemonEntity>?.asDomain(): List<Pokemon> {
    return PokemonEntityMapper.asDomain(this.orEmpty())
}
