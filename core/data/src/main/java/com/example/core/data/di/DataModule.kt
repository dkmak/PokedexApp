package com.example.core.data.di

import com.example.core.data.repository.home.HomeRepository
import com.example.core.data.repository.home.HomeRepositoryImpl
import com.example.core.data.repository.profile.ProfileRepository
import com.example.core.data.repository.profile.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/*
@Bind should use a abstract class, not an interface.
 Because it is just an instruction to the compiler, it's flexible
 */
@Module
@InstallIn(SingletonComponent::class) // creates a single reference that will be injected throughout the lifetime(?) of the application
internal abstract class DataModule { // why internal?
    @Binds
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}
