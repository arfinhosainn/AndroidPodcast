package com.example.androidpodcast.data.remote

import com.example.remote.PodcastApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PodcastApiTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var podcastApi: PodcastApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        podcastApi = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(PodcastApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetCuratedPodcastList() = runBlocking {
        val mockResponse = MockResponse()
        val content = Helper.readFileResources("/curatedlist.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = podcastApi.getCuratedPodcastList()
        mockWebServer.takeRequest()

        Assert.assertEquals(2, response.response.items.size)
        Assert.assertEquals(false, response.response.items.isEmpty())
    }

    @Test
    fun testGetEpisodePodcastByShowId() = runBlocking {
        val mockResponse = MockResponse()
        val content = Helper.readFileResources("/curatedlist.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)
        val showId = "2664729"

        // Act
        val response = podcastApi.getEpisodeForPodcast(showId)
        mockWebServer.takeRequest()

        // Assert
        Assert.assertTrue(response.response.items.isNotEmpty())
        Assert.assertEquals(showId, response.response.items[1].show_id.toString())
    }
}