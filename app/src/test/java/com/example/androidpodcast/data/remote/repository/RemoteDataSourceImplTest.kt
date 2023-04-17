package com.example.androidpodcast.data.remote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidpodcast.data.remote.Helper
import com.example.androidpodcast.data.remote.PodcastApi
import com.example.androidpodcast.data.remote.mappers.Episode
import com.example.androidpodcast.util.Resource
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
    fun `Get recent podcasts should emit loading and success state with correct data`() =
        runBlocking {
            val mockResponse = MockResponse()
            val content = Helper.readFileResources("/response.json")
            mockResponse.setResponseCode(200)
            mockResponse.setBody(content)
            mockWebServer.enqueue(mockResponse)

            val result = repository.getRecentPodcasts().toList()

            assertThat(result).isNotEmpty()
            assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
            assertThat(result[1]).isInstanceOf(Resource.Success::class.java)

            val episodes = (result[1] as Resource.Success<List<Episode>>).data
            assertThat(episodes).isNotEmpty()
            assertThat(episodes!!.size).isEqualTo(2)
            assertThat(episodes[0].show_id).isEqualTo(5686560)
            assertThat(episodes[1].show_id).isEqualTo(2664729)

            // Check the request sent to the web server
            val recordedRequest = mockWebServer.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(recordedRequest.path).isEqualTo("/episodes/recent")
        }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}