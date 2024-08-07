package com.project.nyacawa.domain.adapters.catalog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.databinding.AnimeBlankLongBinding

//Data adapter for vertical lists using long anime blank
class AnimeVerticalListAdapter(
    val list: List<AnimeData>,
    //val animeClick: onAnimeClick TODO( not implemented )
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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: AnimeBlankLongBinding =  convertView?.tag as? AnimeBlankLongBinding ?:
        createBinding(parent.context).apply {
            root.tag = this
        }

        val item = list[position]

        return binding.root
    }

    private fun createBinding(context: Context): AnimeBlankLongBinding {
        return AnimeBlankLongBinding.inflate(LayoutInflater.from(context))
    }

}