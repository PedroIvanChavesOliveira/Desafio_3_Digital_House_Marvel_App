package com.example.desafiodigitalhouse.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiodigitalhouse.model.Result
import com.example.desafiotresdigitalhouse.R
import com.example.desafiotresdigitalhouse.databinding.RecyclerviewCardsHqsBinding

class ComicViewHolder(
    val binding: RecyclerviewCardsHqsBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(movieList: Result, onComicClick: (Result) -> Unit) = with(binding){
        Glide.with(itemView.context)
                .load(movieList.thumbnail?.path)
                .placeholder(R.drawable.marvel_placeholder)
                .into(ivImageRecycler)
        tvHQNumber.text = "#${movieList.issueNumber?.toInt()}"

        itemView.setOnClickListener {
            onComicClick(movieList)
        }
    }
}