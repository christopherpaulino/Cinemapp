package com.frontic.cinemapp.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZWJiMzQ4YTFmZDNiZmZjMTdjYWJmZTkzNmZiMGQwOSIsInN1YiI6IjYxMGYwOGYwZGJmMTQ0MDA0ODE0YjIwZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.QxoPGCRpSI4SQvD9K_9waU2aulK9xC3ajpd0rQLfSFk"

        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder().apply {
            addHeader("Authorization", "Bearer $apiKey")
            addHeader("Content-Type","application/json") }

        val request = requestBuilder.build()

        return chain.proceed(request)
    }


}