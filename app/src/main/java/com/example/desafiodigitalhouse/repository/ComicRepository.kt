package com.example.desafiodigitalhouse.repository

import com.example.desafiodigitalhouse.api.ApiService
import com.example.desafiodigitalhouse.api.ResponseAPI
import java.lang.Exception

class ComicRepository() {
    suspend fun getComics(offset: Int): ResponseAPI {
        return try {
            val response = ApiService.marvelApi.getComics(offset)
            if (response.isSuccessful) {
                ResponseAPI.Success(response.body())
            } else {
                if (response.code() == 404) {
                    ResponseAPI.Error("Dado não encontrado")
                } else {
                    ResponseAPI.Error("Erro ao carregar os dados")
                }
            }
        } catch (exception: Exception) {
            ResponseAPI.Error("Erro ao carregar os dados")
        }
    }
}