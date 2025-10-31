package com.example.core.data.di

import com.example.core.data.repository.home.HomeRepository
import com.example.core.data.repository.home.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class) // creates a single reference that will be injected throughout the lifetime(?) of the application
internal interface DataModule { // why internal?
    @Binds
    fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}