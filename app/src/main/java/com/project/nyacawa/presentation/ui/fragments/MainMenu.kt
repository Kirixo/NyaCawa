package com.project.nyacawa.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.nyacawa.R
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.databinding.FragmentMainMenuBinding
import com.project.nyacawa.domain.adapters.AnimeScrollBarAdapter
import com.project.nyacawa.domain.adapters.onAnimeClick


class MainMenu : Fragment(){

    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        animeListHorizontal(context, binding.thisSesonBar.animeList)
        animeListHorizontal(context, binding.todayBar.animeList)
        animeListHorizontal(context, binding.topBar.animeList)

        binding.thisSesonBar.scrollBarName.text = getString(R.string.this_season)
        binding.todayBar.scrollBarName.text = getString(R.string.today)
        binding.topBar.scrollBarName.text = getString(R.string.top)


        return binding.root
    }


    companion object{


        private fun animeListHorizontal(context: Context?, scrollView: RecyclerView){
            //TODO("The filling should not be here")

            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            scrollView.setLayoutManager(layoutManager)

            val data =  AnimeData(name = "EVANGELION IDI TYDA", poster = context?.let { ContextCompat.getDrawable(it, R.drawable.example) }, description = "WAY", id = 100)
            val list = mutableListOf(data,data,data,data,data,data,data,data)

            val animeClick: onAnimeClick = {data ->
                Toast.makeText(context, data.name, Toast.LENGTH_SHORT).show()
            }

            scrollView.adapter = AnimeScrollBarAdapter(list, animeClick)
        }


    }


}