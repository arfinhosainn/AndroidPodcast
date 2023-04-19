package com.example.androidpodcast.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidpodcast.data.remote.mappers.Episode
import com.example.androidpodcast.data.remote.repository.RemoteDataSourceImpl
import com.example.androidpodcast.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: RemoteDataSourceImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getHomeState() = runTest {
        val emptyListResource = Resource.Success(emptyList<Episode>())
        Mockito.`when`(repository.getRecentPodcasts()).thenReturn(flowOf(emptyListResource))

        val sut = HomeViewModel(repository)
        sut.homeState.value.episodes
        testDispatcher.scheduler.advanceUntilIdle()
        val result = sut.homeState.value.episodes
        Assert.assertEquals(0, result.size)
    }

//    @Test
//    fun  `Get recent podcast should emit success state`()= runBlocking {
//        val episodes  = listOf(Episode())
//    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}