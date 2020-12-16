package com.example.desafiodigitalhouse.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.desafiodigitalhouse.model.Result
import com.example.desafiodigitalhouse.repository.ComicsDataSourceFactory
import com.example.desafiodigitalhouse.utils.Constants.Paging.MAX_PER_PAGE

class ComicsViewModel: ViewModel() {
    var comicPagedList: LiveData<PagedList<Result>>? = null
    private var comicLiveDataSource: LiveData<PageKeyedDataSource<Int, Result>>? = null

    init {
        comicsData()
    }

    private fun comicsData() {
        val marvelSourceFactory = ComicsDataSourceFactory()
        comicLiveDataSource = marvelSourceFactory.getComicsLiveDataSource()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MAX_PER_PAGE).build()

        comicPagedList = LivePagedListBuilder(marvelSourceFactory, pagedListConfig).build()
    }
}