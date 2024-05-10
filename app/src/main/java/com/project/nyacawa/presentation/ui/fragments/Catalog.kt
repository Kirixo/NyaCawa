package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.nyacawa.R
import com.project.nyacawa.databinding.FragmentCatalogBinding
import com.project.nyacawa.domain.adapters.CatalogAdapter
import com.project.nyacawa.domain.adapters.onAnimeClick
import com.project.nyacawa.domain.placeholder.AnimeDataPlaceholder
import com.project.nyacawa.presentation.ui.alert_dialog.FilterDialog

/**
 * A fragment representing a list of Items.
 */
class Catalog : Fragment() {

    private lateinit var binding: FragmentCatalogBinding
    private var columnCount = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)

        // Set the adapter
        with(binding.list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            AnimeDataPlaceholder.setExample(ContextCompat.getDrawable(context, R.drawable.example))

            val click: onAnimeClick = {
                val bundle = Bundle()
                bundle.putParcelable(AnimePlayerFragment.ARG_ITEM_1, it)
                findNavController().navigate(R.id.goToAnimePlayer, bundle)
            }

            adapter = CatalogAdapter(AnimeDataPlaceholder.ITEMS, click)
        }

        binding.fabFilter.setOnClickListener{
            val dialog = FilterDialog()

            dialog.show(parentFragmentManager, "catalog filter")
        }




        return binding.root
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            Catalog().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}