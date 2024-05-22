package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.nyacawa.R
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.data.Comment
import com.project.nyacawa.databinding.FragmentAnimePlayerBinding
import com.project.nyacawa.domain.adapters.CommentsAdapter
import com.project.nyacawa.domain.adapters.onDislikeCommentClick
import com.project.nyacawa.domain.adapters.onLikeCommentClick
import com.project.nyacawa.domain.adapters.onShareCommentClick
import com.project.nyacawa.domain.placeholder.CommentsDataPlaceholder


class AnimePlayerFragment : Fragment() {
    private var param1: AnimeData? = null
    private lateinit var binding: FragmentAnimePlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_ITEM_1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimePlayerBinding.inflate(inflater, container, false)

        binding.animeName.text = param1?.name
        binding.animePoster.setImageBitmap(param1?.poster)
        binding.episodeNumber.text = param1?.episodeCount.toString()
        binding.description.text = param1?.description
        binding.rating.text = param1?.rating.toString()

        val onShareClick: onShareCommentClick = {onShareClick(it)}
        val onDislikeClick: onDislikeCommentClick = {onDislikeClick(it)}
        val onLikeClick: onLikeCommentClick = {onLikeClick(it)}

        with(binding.commentList){
            layoutManager = LinearLayoutManager(context)
            adapter = CommentsAdapter(
                commentList = CommentsDataPlaceholder.ITEMS,
                dislikeAction = onDislikeClick,
                likeCommentClick = onLikeClick,
                onShareCommentClick = onShareClick
            )
        }

        val sendButton = binding.writeComment.getViewById(R.id.send_button) as AppCompatImageButton
        val commentText= binding.writeComment.getViewById(R.id.comment_text) as EditText

        binding.fabAddComment.setOnClickListener{
            binding.writeComment.visibility = VISIBLE
            binding.fabAddComment.visibility = GONE
        }

        commentText.setOnClickListener {
        }

        sendButton.setOnClickListener {
            //TODO(SENDING COMMENT TO SEVER)
            binding.writeComment.visibility = GONE
            commentText.clearFocus()
            binding.fabAddComment.visibility = VISIBLE
        }


        return binding.root
    }

    private fun onSendButtonClick(){

    }
    private fun onDislikeClick(comment: Comment){

    }
    private fun onLikeClick(comment: Comment){

    }
    private fun onShareClick(comment: Comment){

    }

    companion object {
        const val ARG_ITEM_1 = "anime_data"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment AnimePlayer.
         */

        @JvmStatic
        fun newInstance(param1: AnimeData) =
            AnimePlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ITEM_1, param1)
                }
            }
    }
}