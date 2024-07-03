package com.project.nyacawa.domain.adapters.comment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.data.Comment
import com.project.nyacawa.data.User
import com.project.nyacawa.data.UserComment
import com.project.nyacawa.databinding.ViewCommentBlankBinding
import com.project.nyacawa.domain.logic.ProfileButtonStates

class CommentsAdapter(
    private var commentList: List<Comment>,
    private val dislikeAction: onShareCommentClick,
    private val likeCommentClick: onLikeCommentClick,
    private val onShareCommentClick: onShareCommentClick

): RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(
            ViewCommentBlankBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    fun updateItems(newItems: List<Comment>) {
        commentList = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val item = commentList[position]
        Log.d("[COMMENTS ADAPTER]", "onBindViewHolder: bind")

        val user = UserComment(null, item.username, item.status)
        holder.accountView.setProfile(user, ProfileButtonStates.SHARE)
        holder.accountView.setShareButton(onShareCommentClick, item)

        holder.like.setOnClickListener {
            likeCommentClick.invoke(item)
        }

        holder.dislike.setOnClickListener {
            dislikeAction.invoke(item)
        }

        holder.text.text = item.text
        holder.rating.text = 0.toString()
    }

    inner class CommentsViewHolder(binding: ViewCommentBlankBinding) : RecyclerView.ViewHolder(binding.root) {
        val accountView = binding.account
        val dislike = binding.dislike
        val like = binding.like
        val text = binding.commentText
        val rating = binding.rating
    }
}