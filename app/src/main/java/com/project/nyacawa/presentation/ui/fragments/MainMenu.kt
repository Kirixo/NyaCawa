package com.project.nyacawa.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.nyacawa.R
import com.project.nyacawa.databinding.FragmentMainMenuBinding
import com.project.nyacawa.domain.adapters.AnimeScrollBarAdapter
import com.project.nyacawa.domain.adapters.onAnimeClick
import com.project.nyacawa.domain.placeholder.AnimeDataPlaceholder
import androidx.navigation.fragment.findNavController


class MainMenu : Fragment(){

    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        val click: onAnimeClick = {
            val bundle = Bundle()
            bundle.putParcelable(AnimePlayerFragment.ARG_ITEM_1, it)
            findNavController().navigate(R.id.goToAnimePlayer, bundle)
        }

        animeListHorizontal(context, binding.thisSesonBar.animeList, click)
        animeListHorizontal(context, binding.todayBar.animeList, click)
        animeListHorizontal(context, binding.topBar.animeList, click)

        binding.thisSesonBar.scrollBarName.text = getString(R.string.this_season)
        binding.todayBar.scrollBarName.text = getString(R.string.today)
        binding.topBar.scrollBarName.text = getString(R.string.top)


        return binding.root
    }


    companion object{
        private fun animeListHorizontal(context: Context?, scrollView: RecyclerView, onAnimeClick: onAnimeClick){
            //TODO("The filling should not be here")

            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            scrollView.setLayoutManager(layoutManager)

            val dataList =  AnimeDataPlaceholder.ITEMS

            scrollView.adapter = AnimeScrollBarAdapter(dataList, onAnimeClick)
        }
    }


}