package com.project.nyacawa.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.project.nyacawa.R
import com.project.nyacawa.domain.adapters.AnimeVerticalListAdapter
import com.project.nyacawa.domain.logic.SearchViewModel
import kotlinx.coroutines.launch

class AnimeSearchList : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private var adapter: AnimeVerticalListAdapter = AnimeVerticalListAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    //adapter.notifyDataSetChanged()
                    Toast.makeText(context, "Search text changed", LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_anime_list_list, container, false)

        //adapter = AnimeVerticalListAdapter()


        return view
    }

}