package com.project.nyacawa.domain.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.databinding.AnimeBlankLongBinding
import com.project.nyacawa.databinding.AnimeBlankSmallBinding

class AnimeScrollBarAdapter (
    val list: MutableList<AnimeData>,
    val animeClick: onAnimeClick
): RecyclerView.Adapter<AnimeScrollBarAdapter.AnimeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AnimeBlankSmallBinding.inflate(inflater, parent, false)
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, animeClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    class AnimeViewHolder(val binding: AnimeBlankSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(animeData: AnimeData, itemClick: (AnimeData) -> Unit) {
            binding.animePoster.setImageBitmap(animeData.poster)
            binding.animeName.text = animeData.name
            binding.root.setOnClickListener {
                itemClick(animeData)
            }
        }
    }
}