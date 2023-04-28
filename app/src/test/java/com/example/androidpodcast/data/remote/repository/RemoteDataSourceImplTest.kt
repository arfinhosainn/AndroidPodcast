package com.example.androidpodcast.data.remote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.RemoteDataSourceImpl
import com.example.androidpodcast.data.remote.Helper
import com.example.mappers.Episode
import com.example.model.EpisodeSong
import com.example.model.PodcastList
import com.example.remote.PodcastApi
import com.example.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteDataSourceImplTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var podcastApi: PodcastApi

    lateinit var mockWebServer: MockWebServer

    private lateinit var repository: RemoteDataSourceImpl

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        podcastApi = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(PodcastApi::class.java)
        repository = RemoteDataSourceImpl(podcastApi)
    }

    @Test
    fun `Get curated list podcasts returns loading and success state`() =
        runBlocking {
            val mockResponse = MockResponse()
            val content = Helper.readFileResources("/curatedlist.json")
            mockResponse.setResponseCode(200)
            mockResponse.setBody(content)
            mockWebServer.enqueue(mockResponse)

            val result = repository.getCuratedPodcastList().toList()

            assertThat(result).isNotEmpty()
            assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
            assertThat(result[1]).isInstanceOf(Resource.Success::class.java)

            val episodes = (result[1] as Resource.Success<List<PodcastList>>).data
            assertThat(episodes).isNotEmpty()
            assertThat(episodes!!.size).isEqualTo(2)
            assertThat(episodes[0].show_id).isEqualTo(5686560)
            assertThat(episodes[1].show_id).isEqualTo(2664729)

            // Check the request sent to the web server
            val recordedRequest = mockWebServer.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(recordedRequest.path).isEqualTo(
                "/explore/lists/108/items?limit=60"
            )
        }

    @Test
    fun `Get curated list emit loading and throws HttpException`() =
        runBlocking {
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(500)
            mockWebServer.enqueue(mockResponse)

            val result = repository.getCuratedPodcastList().toList()
            assertThat(result.size).isEqualTo(2)
            assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
            assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
            assertThat((result[1] as Resource.Error).message).isEqualTo("HTTP 500 Server Error")
        }

    @Test
    fun `Get recent podcasts should emit loading and success state with correct data`() =
        runBlocking {
            val mockResponse = MockResponse()
            val content = Helper.readFileResources("/recentpodcast.json")
            mockResponse.setResponseCode(200)
            mockResponse.setBody(content)
            mockWebServer.enqueue(mockResponse)

            val result = repository.getRecentPodcasts().toList()

            assertThat(result).isNotEmpty()
            assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
            assertThat(result[1]).isInstanceOf(Resource.Success::class.java)

            val episodes = (result[1] as Resource.Success<List<Episode>>).data
            assertThat(episodes).isNotEmpty()
            assertThat(episodes!!.size).isEqualTo(5)
            assertThat(episodes[0].show_id).isEqualTo(1484208)
            assertThat(episodes[1].show_id).isEqualTo(2888461)

            // Check the request sent to the web server
            val recordedRequest = mockWebServer.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(recordedRequest.path).isEqualTo(
                "/tags/android+development/episodes?limit=20"
            )
        }

    @Test
    fun `Get Recent Podcasts emit loading and throws HttpException`() =
        runBlocking {
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(500)
            mockWebServer.enqueue(mockResponse)

            val result = repository.getRecentPodcasts().toList()
            assertThat(result.size).isEqualTo(2)
            assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
            assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
            assertThat((result[1] as Resource.Error).message).isEqualTo("HTTP 500 Server Error")
        }

    @Test
    fun `Get episodes for podcast returns loading and success state`() = runBlocking {
        // Create a mock response with two episodes, one with the correct showId and one with a different showId
        val mockResponse = MockResponse()
        val content = Helper.readFileResources("/recentpodcast.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        // Call the function with the correct showId
        val result = repository.getEpisodeForPodcast("1484208").toList()

        // Verify that the result contains a loading state and a success state with the expected episodes
        assertThat(result).hasSize(2)
        assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Success::class.java)
        val episodes = (result[1] as Resource.Success<List<EpisodeSong>>).data
        assertThat(episodes).hasSize(5)
        assertThat(episodes!![0].show_id).isEqualTo(1484208)

        // Check the request sent to the web server
        val recordedRequest = mockWebServer.takeRequest()
        assertThat(recordedRequest.method).isEqualTo("GET")
        assertThat(recordedRequest.path).isEqualTo("/shows/1484208/episodes")
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}