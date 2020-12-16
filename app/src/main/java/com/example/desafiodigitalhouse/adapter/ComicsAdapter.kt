package com.example.desafiodigitalhouse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.example.desafiodigitalhouse.model.Result
import com.example.desafiotresdigitalhouse.databinding.RecyclerviewCardsHqsBinding

class ComicsAdapter(
    private val onComicClick: (Result?) -> Unit
): PagedListAdapter<Result, ComicViewHolder>(Result.DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewCardsHqsBinding.inflate(layoutInflater, parent, false)
        return ComicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onComicClick) }
    }
}