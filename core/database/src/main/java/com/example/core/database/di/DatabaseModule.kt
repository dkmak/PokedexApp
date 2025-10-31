package com.example.core.database.di

import android.app.Application
import androidx.room.Room
import com.example.core.database.PokedexDatabase
import com.example.core.database.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
    ): PokedexDatabase {
        return Room
            .databaseBuilder(application, PokedexDatabase::class.java, "Pokedex.db")
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(appDatabase: PokedexDatabase): PokemonDao = appDatabase.pokemonDao()
}