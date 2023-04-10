package com.example.androidpodcast.di

import com.example.androidpodcast.data.remote.ApiKeyInterceptor
import com.example.androidpodcast.data.remote.PodcastApi
import com.example.androidpodcast.data.remote.repository.RemoteDataSourceImpl
import com.example.androidpodcast.domain.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl("https://api.spreaker.com/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptor: ApiKeyInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun providesPodcastApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): PodcastApi {
        return retrofitBuilder.client(okHttpClient).build().create(PodcastApi::class.java)
    }

    @Singleton
    @Provides
    fun providesRemoteDataSource(api: PodcastApi): RemoteDataSource {
        return RemoteDataSourceImpl(api)
    }
}