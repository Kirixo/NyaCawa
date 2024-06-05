package com.project.nyacawa.domain.adapters.catalog

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.nyacawa.R
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.databinding.AnimeBlankSmallBinding
import com.squareup.picasso.Picasso

class AnimeScrollBarAdapter (
    val list: List<AnimeData>,
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
            Picasso.get()
                .load(animeData.image)
                .placeholder(R.drawable.example)
                .into(binding.animePoster)

            binding.animeName.text = animeData.name
            binding.root.setOnClickListener {
                itemClick(animeData)
            }
        }
    }
}