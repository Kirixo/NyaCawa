package com.project.nyacawa.domain.placeholder

import com.project.nyacawa.data.FavoriteItem
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object FavoriteListPlaceholder {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<FavoriteItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<Int, FavoriteItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i))
        }
    }

    private fun addItem(item: FavoriteItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }
    public fun deleteItem(item: FavoriteItem) {
        ITEM_MAP.remove(item.id)
        ITEMS.remove(item)
    }



    private fun createPlaceholderItem(position: Int): FavoriteItem {
        return FavoriteItem(position)
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A placeholder item representing a piece of content.
     */
    data class PlaceholderItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}