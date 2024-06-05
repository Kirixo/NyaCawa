package com.project.nyacawa.domain.adapters.favorite

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.project.nyacawa.data.FavoriteItem

import com.project.nyacawa.databinding.FragmentFavorite2Binding


class FavoriteItemAdapter(
    private val values: List<FavoriteItem>,
    private val onHeartClick: onHeartClick
) : RecyclerView.Adapter<FavoriteItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentFavorite2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.heartButton.setOnClickListener {
            onHeartClick.invoke(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentFavorite2Binding) :RecyclerView.ViewHolder(binding.root) {
        val heartButton: ImageView = binding.favButton

    }

}