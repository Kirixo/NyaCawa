package com.project.nyacawa.domain.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.project.nyacawa.data.AnimeData

class AnimeScrollBarAdapter (
    val animeList : List<AnimeData>
) : BaseAdapter() {
    override fun getCount(): Int {
        return animeList.size;
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }
}