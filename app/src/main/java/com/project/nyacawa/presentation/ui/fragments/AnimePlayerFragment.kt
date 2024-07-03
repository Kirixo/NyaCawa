package com.project.nyacawa.presentation.ui.fragments

import android.app.Activity
import android.content.Context
import androidx.media3.common.MediaItem
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.nyacawa.R
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.data.Comment
import com.project.nyacawa.data.UserDataCache
import com.project.nyacawa.databinding.FragmentAnimePlayerBinding
import com.project.nyacawa.domain.adapters.comment.CommentsAdapter
import com.project.nyacawa.domain.adapters.comment.onDislikeCommentClick
import com.project.nyacawa.domain.adapters.comment.onLikeCommentClick
import com.project.nyacawa.domain.adapters.comment.onShareCommentClick
import com.project.nyacawa.domain.logic.CommentsViewModel
import com.project.nyacawa.domain.logic.UserViewModel
import com.project.nyacawa.presentation.ui.views.AccountField
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AnimePlayerFragment : Fragment() {
    private var param1: AnimeData? = null
    private lateinit var binding: FragmentAnimePlayerBinding
    private lateinit var player: ExoPlayer
    private lateinit var fadapter: CommentsAdapter

    private val commentsModel: CommentsViewModel by viewModels()
    private val userModel: UserViewModel by activityViewModels()
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

        Picasso.get()
            .load(param1?.image)
            .placeholder(R.drawable.example)
            .into(binding.animePoster)

        binding.animeName.text = param1?.name
        binding.episodeNumber.text = param1?.total_episodes.toString()
        binding.description.text = param1?.description
        binding.rating.text = param1?.general_score.toString()

        binding.animeSeason.text = "Start of ${convertUnixToDateString(param1?.aired_start)}, end of ${convertUnixToDateString(param1?.aired_end)}"

        val onShareClick: onShareCommentClick = {onShareClick(it)}
        val onDislikeClick: onDislikeCommentClick = {onDislikeClick(it)}
        val onLikeClick: onLikeCommentClick = {onLikeClick(it)}


        fadapter = CommentsAdapter(
            commentList = emptyList(),
            dislikeAction = onDislikeClick,
            likeCommentClick = onLikeClick,
            onShareCommentClick = onShareClick
        )


        with(binding.commentList){
            layoutManager = LinearLayoutManager(context)
            adapter = fadapter
        }


        val sendButton = binding.writeComment.getViewById(R.id.send_button) as AppCompatImageButton
        val commentText= binding.writeComment.getViewById(R.id.comment_text) as EditText
        val user = binding.writeComment.getViewById(R.id.account) as AccountField

        binding.fabAddComment.setOnClickListener{
            binding.writeComment.visibility = VISIBLE
            binding.fabAddComment.visibility = GONE
        }

        commentText.setOnClickListener {
        }

        val cache = UserDataCache(requireContext())
        sendButton.setOnClickListener {
            val userId = cache.getUserCache().userId
            commentsModel.sendComment(commentText.text.toString(), userId,  param1!!.id.toInt())

            user.setUser(userModel.user.value!!)

            binding.writeComment.visibility = GONE
            binding.fabAddComment.visibility = VISIBLE
        }

        commentsModel.getComments(param1!!.id.toInt())
        commentsModel.comments.observe(requireActivity()){
            fadapter.updateItems(it)
        }


        // Init player
        player = ExoPlayer.Builder(this.requireContext()).build()
        binding.player.player = player

//        val uri: Uri = Uri.parse("https://www.secvideo1.online/get_file/10/6507e18d290f71f1ee72b5c63d73490f8b45a5bcd5/783000/783677/783677.mp4")
        val uri: Uri = Uri.parse("")

        val mediaItem = MediaItem.fromUri(uri)

        player.prepare()
        player.play()
        player.playWhenReady = true

        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                android.util.Log.d("ExoPlayer", "Error: ${error.message}")
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    android.util.Log.d("ExoPlayer", "Playback started")
                } else {
                    android.util.Log.d("ExoPlayer", "Playback paused")
                }
            }
        })

        player.setMediaItem(mediaItem)
        return binding.root
    }

    private fun convertUnixToDateString(unixTime: Int?): String {
        return if (unixTime != null) {
            val date = Date(unixTime.toLong() * 1000)
            val sdf = SimpleDateFormat("d.M.y", Locale.getDefault())
            sdf.format(date)
        } else {
            "N/A"
        }
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
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

        fun hideKeyboard(activity: Activity) {
            val view: View? = activity.currentFocus
            view?.let {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        @JvmStatic
        fun newInstance(param1: AnimeData) =
            AnimePlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ITEM_1, param1)
                }
            }
    }
}