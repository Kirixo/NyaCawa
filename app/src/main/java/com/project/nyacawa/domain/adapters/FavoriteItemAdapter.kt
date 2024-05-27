package com.project.nyacawa.domain.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.project.nyacawa.domain.placeholder.FavoriteListPlaceholder.PlaceholderItem
import com.project.nyacawa.databinding.FragmentFavorite2Binding


class FavoriteItemAdapter(
    private val values: List<PlaceholderItem>
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
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentFavorite2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        override fun toString(): String {
            return super.toString();
        }
    }

}