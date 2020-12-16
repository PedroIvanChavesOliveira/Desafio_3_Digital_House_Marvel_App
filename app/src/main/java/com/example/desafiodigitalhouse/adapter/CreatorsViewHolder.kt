package com.example.desafiodigitalhouse.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.desafiodigitalhouse.model.Item
import com.example.desafiotresdigitalhouse.databinding.RecyclerviewCreatorsBinding

class CreatorsViewHolder(
        val binding: RecyclerviewCreatorsBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(creator: Item) = with(binding){
        tvCreatorsName.text = creator?.name
        tvCreatorsRole.text = "${creator?.role}:"
    }
}