package com.example.di

import com.example.ApiKeyInterceptor
import com.example.PodcastApi
import com.example.repository.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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
        return OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(interceptor).build()
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