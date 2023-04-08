package com.example.androidpodcast.data.remote

import com.example.androidpodcast.BuildConfig
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()

        request.addHeader("X-ListenAPI-Key", value = BuildConfig.API_KEY)
        return chain.proceed(request.build())
    }
}