package com.project.nyacawa.domain.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.databinding.FragmentAnimeListBinding

class AnimeVerticalListAdapter(
    val list: List<AnimeData>,
    //val animeClick: onAnimeClick
    ): BaseAdapter(){
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO()
    }

    private fun createBinding(context: Context){
        TODO()
    }

}