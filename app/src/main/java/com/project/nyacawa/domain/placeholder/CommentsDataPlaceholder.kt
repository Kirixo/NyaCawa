package com.project.nyacawa.domain.placeholder

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.data.Comment
import com.project.nyacawa.data.Profile
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */

object CommentsDataPlaceholder {

    val ITEMS: MutableList<Comment> = ArrayList()
    private val ITEM_MAP: MutableMap<Int, Comment> = HashMap()


    private const val COUNT = 1000

    init{
        for (i in 0..COUNT){
            addItem(createPlaceholderItem())
        }
    }
    private fun addItem(item: Comment) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createPlaceholderItem(): Comment {
        return Comment(
            id = ITEMS.size,
            profile = Profile(1),
            text = "Comment #" + ITEMS.size,
            likes = ITEMS.size,
            dislikes = COUNT/2
        )
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        return builder.toString()
    }
}