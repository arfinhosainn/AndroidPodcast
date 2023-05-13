package com.example.core_di

import android.content.Context
import androidx.room.Room
import com.example.RemoteDataSourceImpl
import com.example.local.PodcastDatabase
import com.example.remote.ApiKeyInterceptor
import com.example.remote.PodcastApi
import com.example.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesRemoteDataSource(api: PodcastApi, db: PodcastDatabase): RemoteDataSource {
        return RemoteDataSourceImpl(api, db)
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

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PodcastDatabase {
        return Room.databaseBuilder(
            context,
            PodcastDatabase::class.java,
            "podcast_database"
        ).build()
    }


}