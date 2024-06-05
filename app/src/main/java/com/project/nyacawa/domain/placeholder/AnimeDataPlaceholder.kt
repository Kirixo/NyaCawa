package com.project.nyacawa.domain.placeholder

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.project.nyacawa.data.AnimeData
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */

object AnimeDataPlaceholder {

    val ITEMS: MutableList<AnimeData> = ArrayList()
    private val ITEM_MAP: MutableMap<Long, AnimeData> = HashMap()


    private const val COUNT = 25

    fun setExample(drawable: Drawable?){
        for(i in 1..COUNT){
            addItem(createPlaceholderItem(name = "EVANGELION IDI TYDA", poster = drawable, descriptor = "WAY", id = 100, rating = 10.0f, episodeCount = 24))
        }
    }

    private fun addItem(item: AnimeData) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createPlaceholderItem(id: Long, name: String, descriptor: String, poster: Drawable? = null, rating: Float, episodeCount: Int ): AnimeData {
        return AnimeData(0, 0, descriptor, rating, id, null, name, episodeCount)
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        return builder.toString()
    }
}