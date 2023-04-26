package com.example

import com.example.androidpodcast.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()

        request.addHeader("X-ListenAPI-Key", value = BuildConfig.API_KEY)
        return chain.proceed(request.build())
    }
}