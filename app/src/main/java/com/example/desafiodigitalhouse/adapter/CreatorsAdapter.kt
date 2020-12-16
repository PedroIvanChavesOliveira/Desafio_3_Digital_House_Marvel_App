package com.example.desafiodigitalhouse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiodigitalhouse.model.Item
import com.example.desafiotresdigitalhouse.databinding.RecyclerviewCreatorsBinding

class CreatorsAdapter(
        val creatorsList: List<Item>
): RecyclerView.Adapter<CreatorsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewCreatorsBinding.inflate(layoutInflater, parent, false)
        return CreatorsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return creatorsList.size
    }

    override fun onBindViewHolder(holder: CreatorsViewHolder, position: Int) {
        holder.bind(creatorsList[position])
    }
}