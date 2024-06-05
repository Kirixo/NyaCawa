package com.project.nyacawa.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.nyacawa.R
import com.project.nyacawa.databinding.FragmentMainMenuBinding
import com.project.nyacawa.domain.adapters.catalog.AnimeScrollBarAdapter
import com.project.nyacawa.domain.adapters.catalog.onAnimeClick
import com.project.nyacawa.domain.placeholder.AnimeDataPlaceholder
import androidx.navigation.fragment.findNavController
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.domain.logic.CatalogAnimeListViewModel


class MainMenu : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding

    private lateinit var adapterThisSeason: AnimeScrollBarAdapter
    private lateinit var adapterToday: AnimeScrollBarAdapter
    private lateinit var adapterTop: AnimeScrollBarAdapter

    private val catalogList: CatalogAnimeListViewModel by activityViewModels()

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

        catalogList.topAnimeList.observe(viewLifecycleOwner) { data ->
            setDataList(binding.topBar.animeList, data , click)
        }

        catalogList.todayAnimeList.observe(viewLifecycleOwner) { data ->
            setDataList(binding.todayBar.animeList, data , click)
        }

        catalogList.thisSeasonAnimeList.observe(viewLifecycleOwner) { data ->
            setDataList(binding.thisSesonBar.animeList, data , click)
        }

        animeListHorizontal(context, binding.thisSesonBar.animeList, click)
        animeListHorizontal(context, binding.todayBar.animeList, click)
        animeListHorizontal(context, binding.topBar.animeList, click)

        binding.thisSesonBar.scrollBarName.text = getString(R.string.this_season)
        binding.todayBar.scrollBarName.text = getString(R.string.today)
        binding.topBar.scrollBarName.text = getString(R.string.top)

        return binding.root
    }

    private fun setDataList(scrollView: RecyclerView, dataList: List<AnimeData>, onAnimeClick: onAnimeClick) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        scrollView.layoutManager = layoutManager
        scrollView.adapter = AnimeScrollBarAdapter(dataList, onAnimeClick)
    }

    private fun animeListHorizontal(context: Context?, scrollView: RecyclerView, onAnimeClick: onAnimeClick) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        scrollView.layoutManager = layoutManager
        val dataList = AnimeDataPlaceholder.ITEMS
        scrollView.adapter = AnimeScrollBarAdapter(dataList, onAnimeClick)
    }
}