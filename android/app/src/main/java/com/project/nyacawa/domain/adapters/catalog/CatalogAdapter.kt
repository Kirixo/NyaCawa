package com.project.nyacawa.domain.adapters.catalog

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.project.nyacawa.R
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.databinding.AnimeBlankSmallBinding
import com.squareup.picasso.Picasso

class CatalogAdapter(
    private var values: List<AnimeData>,
    private val onAnimeClick: onAnimeClick
) : RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AnimeBlankSmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name

        Picasso.get()
            .load(item.image)
            .placeholder(R.drawable.example)
            .into(holder.poster)

        holder.poster.setOnClickListener { onAnimeClick.invoke(item) }
        holder.score.text = String.format("%.2f", item.general_score)
    }

    override fun getItemCount(): Int = values.size

    fun updateItems(newItems: List<AnimeData>) {
        values = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: AnimeBlankSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.animeName
        val poster: ImageView = binding.animePoster
        val score: TextView = binding.score
    }
}
