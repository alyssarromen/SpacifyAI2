package com.example.spacify_ai

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.decor8.ai/"

    // Set up Retrofit with logging interceptor
    private val retrofit: Retrofit by lazy {
        // Create a logging interceptor
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs request and response body
        }

        // Attach logging interceptor to OkHttpClient
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // Build Retrofit with OkHttp client
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Set the custom OkHttp client
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Initialize API service
    val apiService: Decor8ApiService by lazy {
        retrofit.create(Decor8ApiService::class.java)
    }
}
