package com.example.desafiodigitalhouse.business

import androidx.paging.PageKeyedDataSource
import com.example.desafiodigitalhouse.api.ResponseAPI
import com.example.desafiodigitalhouse.extensions.getPhotoPath
import com.example.desafiodigitalhouse.model.Comics
import com.example.desafiodigitalhouse.model.Data
import com.example.desafiodigitalhouse.model.Result
import com.example.desafiodigitalhouse.repository.ComicRepository
import com.example.desafiodigitalhouse.utils.Constants.Paging.FIRST_PAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComicsPageKeyedDataSource(): PageKeyedDataSource<Int, Result>() {
    private val repository by lazy {
        ComicRepository()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            when(val response = repository.getComics(FIRST_PAGE)) {
                is ResponseAPI.Success -> {
                    val data = response.data as Comics
                    data.data?.results?.forEach {result ->
                        result.thumbnail?.path = result.thumbnail?.path?.getPhotoPath()
                        result.images?.forEach {
                            it.path = it.path?.getPhotoPath()
                        }
                        if(result.description == null) {
                            result.description = "Descrição não Encontrada"
                        }
                    }
                    data.data?.results?.let { callback.onResult(it, null, FIRST_PAGE + 1) }
                }
                is ResponseAPI.Error -> {
                    callback.onResult(mutableListOf(), null, FIRST_PAGE + 1)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        val page = params.key
        CoroutineScope(Dispatchers.IO).launch {
            when(val response = repository.getComics(page)) {
                is ResponseAPI.Success -> {
                    val data = response.data as Comics
                    data.data?.results?.forEach {result ->
                        result.images?.forEach {
                            it.path = it.path?.getPhotoPath()
                        }
                        if(result.description == null) {
                            result.description = "Descrição não Encontrada"
                        }
                        result.thumbnail?.path = result.thumbnail?.path?.getPhotoPath()
                    }
                    data.data?.results?.let { callback.onResult(it,  page + 1) }
                }
                is ResponseAPI.Error -> {
                    callback.onResult(mutableListOf(), page + 1)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        val page = params.key
        CoroutineScope(Dispatchers.IO).launch {
            when(val response = repository.getComics(page)) {
                is ResponseAPI.Success -> {
                    val data = response.data as Comics
                    data.data?.results?.forEach {result ->
                        result.images?.forEach {
                            it.path = it.path?.getPhotoPath()
                        }
                        if(result.description == null) {
                            result.description = "Descrição não Encontrada"
                        }
                        result.thumbnail?.path = result.thumbnail?.path?.getPhotoPath()
                    }
                    data.data?.results?.let { callback.onResult(it,  page - 1) }
                }
                is ResponseAPI.Error -> {
                    callback.onResult(mutableListOf(), page - 1)
                }
            }
        }
    }
}