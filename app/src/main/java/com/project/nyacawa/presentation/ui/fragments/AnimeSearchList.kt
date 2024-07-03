package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.project.nyacawa.databinding.FragmentAnimeListListBinding
import com.project.nyacawa.domain.adapters.catalog.AnimeVerticalListAdapter
import com.project.nyacawa.domain.logic.SearchViewModel
import kotlinx.coroutines.launch

class AnimeSearchList : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private var adapter: AnimeVerticalListAdapter = AnimeVerticalListAdapter(emptyList())

    private lateinit var binding: FragmentAnimeListListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Subscribe on flow for updates tracking
        //worked async with fragment on de-attach fragment coroutine stops
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { newState ->
                    Log.d("[SEARCH SEARCH FRAGMENT]", "text " + newState.searchText)
                    Toast.makeText(context, "Search text changed: ${newState.searchText}", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeListListBinding.inflate(inflater, container, false)
        binding.animeSearchList.adapter = adapter
        //adapter = AnimeVerticalListAdapter()

        return binding.root
    }

}