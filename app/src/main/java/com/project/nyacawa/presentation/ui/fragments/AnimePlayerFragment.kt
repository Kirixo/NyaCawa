package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.nyacawa.R
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.databinding.FragmentAnimePlayerBinding



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

        return binding.root
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