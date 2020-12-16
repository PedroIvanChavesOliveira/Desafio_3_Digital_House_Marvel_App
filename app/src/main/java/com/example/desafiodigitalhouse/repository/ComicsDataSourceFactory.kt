package com.example.desafiodigitalhouse.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.desafiodigitalhouse.business.ComicsPageKeyedDataSource
import com.example.desafiodigitalhouse.model.Result

class ComicsDataSourceFactory(): DataSource.Factory<Int, Result>(){
    private val marvelLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Result>>()

    override fun create(): DataSource<Int, Result> {
        val marvelDataSource = ComicsPageKeyedDataSource()

        marvelLiveDataSource.postValue(marvelDataSource)
        return marvelDataSource
    }

    fun getComicsLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, Result>> {
        return marvelLiveDataSource
    }
}