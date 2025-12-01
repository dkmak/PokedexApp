package com.example.feature.home

import com.example.core.data.repository.home.HomeRepository
import com.example.model.Pokemon
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var homeRepository: HomeRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        homeRepository = mockk()
    }


    @After
    fun after(){
        Dispatchers.resetMain()
    }

    @Test
    fun `pokemonList state emits data from repository`() = runTest(testDispatcher) {
        coEvery { homeRepository.fetchPokemonList(any()) } returns flowOf(
            Result.success(listOf(Pokemon(nameField = "Bulbasaur", url = "url/1/")))
        )
        viewModel = HomeViewModel(homeRepository)

        val job = launch(testDispatcher) { viewModel.pokemonList.collect{} }
        advanceUntilIdle()

        val pokemonList = viewModel.pokemonList.value

        Assert.assertEquals(1, pokemonList.size)
        Assert.assertEquals("Bulbasaur", pokemonList[0].nameField)
        job.cancel()
    }


    @Test
    fun `fetchNextPokemonList increments fetching index and triggers new flow`() = runTest {
        val page0_Pokemon = listOf(Pokemon(nameField = "Bulbasaur", url = "url/1/"))
        val page1_Pokemon = listOf(Pokemon(nameField = "Ivysaur", url = "url/2/"))

        coEvery { homeRepository.fetchPokemonList(0) } returns flowOf(Result.success(page0_Pokemon))
        coEvery { homeRepository.fetchPokemonList(1) } returns flowOf(Result.success(page1_Pokemon))
        viewModel = HomeViewModel(homeRepository)

        val job = launch(testDispatcher) { viewModel.pokemonList.collect{} }

        viewModel.fetchNextPokemonList() // Call the function to increment the page
        testDispatcher.scheduler.advanceUntilIdle() // Ensure coroutines complete


        val latestPokemonList = viewModel.pokemonList.first()
        Assert.assertEquals("Ivysaur", latestPokemonList[0].nameField)
        job.cancel()
    }

    @Test
    fun `isLoading is true during fetch and false after it completes`() = runTest{
        coEvery { homeRepository.fetchPokemonList(any()) } returns flow {
            delay(100)
            emit(Result.success(listOf(Pokemon(nameField = "Bulbasaur", url = "url/1/"))))
        }

        viewModel = HomeViewModel(homeRepository)

        val job = launch(testDispatcher) { viewModel.pokemonList.collect{} }
        testDispatcher.scheduler.runCurrent() // run tasks that are ready to execute right now

        Assert.assertTrue("isLoading should be true immediately after collection starts.", viewModel.isLoading.value)
        advanceUntilIdle()


        Assert.assertFalse("isLoading should be false after data is emitted.", viewModel.isLoading.value)
        job.cancel()
    }
}