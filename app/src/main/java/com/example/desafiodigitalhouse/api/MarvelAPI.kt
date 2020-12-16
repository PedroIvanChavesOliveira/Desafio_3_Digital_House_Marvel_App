package com.example.desafiodigitalhouse.api

import com.example.desafiodigitalhouse.model.Comics
import com.example.desafiodigitalhouse.model.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPI {
    @GET("comics")
    suspend fun getComics(
        @Query("offset") offsetNumber: Int
    ): Response<Comics>
}