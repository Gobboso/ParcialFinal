package com.example.parcialfinal.data

import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface AlbumsApi {
    @GET("/albums")
    suspend fun getAlbums(): List<Album>

    companion object {
        fun create(): AlbumsApi {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            val client = OkHttpClient.Builder().addInterceptor(logger).build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(AlbumsApi::class.java)
        }
    }
}
