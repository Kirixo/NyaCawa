package com.project.nyacawa.domain.adapters.favorite

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.project.nyacawa.R
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.data.FavoriteItem

import com.project.nyacawa.databinding.FragmentFavorite2Binding
import com.squareup.picasso.Picasso


class FavoriteItemAdapter(
    private var values: List<FavoriteItem>,
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
    fun updateItems(newItems: List<FavoriteItem>) {
        values = newItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.heartButton.setOnClickListener {
            onHeartClick.invoke(item)
        }
        holder.price.text = item.price.toString() + " ₴"
        holder.label.text = item.name

        Picasso.get()
            .load(item.image)
            .placeholder(R.drawable.example)
            .into(holder.imageView)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentFavorite2Binding) :RecyclerView.ViewHolder(binding.root) {
        val heartButton: ImageView = binding.favButton
        val imageView: ImageView = binding.img
        val price: TextView = binding.price
        val label: TextView = binding.label

    }

}