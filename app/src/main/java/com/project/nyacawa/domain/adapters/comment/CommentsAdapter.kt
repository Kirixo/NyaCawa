package com.project.nyacawa.domain.adapters.comment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.nyacawa.data.Comment
import com.project.nyacawa.databinding.ViewCommentBlankBinding
import com.project.nyacawa.domain.logic.ProfileButtonStates

class CommentsAdapter(
    private val commentList: List<Comment>,
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

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val item = commentList[position]
        Log.d("[COMMENTS ADAPTER]", "onBindViewHolder: bind")
        if(item.user!=null){
            holder.accountView.setProfile(item.user, ProfileButtonStates.SHARE)
            holder.accountView.setShareButton(onShareCommentClick, item)

            holder.like.setOnClickListener {
                likeCommentClick.invoke(item)
            }

            holder.dislike.setOnClickListener {
                dislikeAction.invoke(item)
            }

            holder.text.text = item.text
            holder.rating.text = item.getRating().toString()
            holder.rating.setTextColor(item.getRatingColor())
            //TODO(GET INFORMATION FROM BD THAT THIS COMMENT IS LIKED OR DISLIKED BY THIS USER)
        }
    }

    inner class CommentsViewHolder(binding: ViewCommentBlankBinding) : RecyclerView.ViewHolder(binding.root) {
        val accountView = binding.account
        val dislike = binding.dislike
        val like = binding.like
        val text = binding.commentText
        val rating = binding.rating
    }
}