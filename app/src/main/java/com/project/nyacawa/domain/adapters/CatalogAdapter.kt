package com.project.nyacawa.domain.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.databinding.AnimeBlankSmallBinding

class CatalogAdapter(
    private val values: List<AnimeData>
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
        holder.poster.setImageDrawable(item.poster)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: AnimeBlankSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.animeName
        val poster: ImageView = binding.animePoster

    }

}