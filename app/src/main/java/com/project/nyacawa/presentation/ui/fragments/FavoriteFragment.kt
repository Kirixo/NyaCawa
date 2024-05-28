package com.project.nyacawa.presentation.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.nyacawa.R
import com.project.nyacawa.domain.adapters.FavoriteItemAdapter
import com.project.nyacawa.domain.adapters.onHeartClick
import com.project.nyacawa.domain.placeholder.FavoriteListPlaceholder

/**
 * A fragment representing a list of Items.
 */
class FavoriteFragment : Fragment() {

    private var columnCount = 2
    private lateinit var fadapter: FavoriteItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_list, container, false)
        val favoriteItemList = FavoriteListPlaceholder.ITEMS

        val onFavClick: onHeartClick = {
            val builder = AlertDialog.Builder(requireContext())

            builder.setMessage(getString(R.string.deletion_request))

            builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                FavoriteListPlaceholder.deleteItem(it)
                fadapter.notifyItemRemoved(FavoriteListPlaceholder.ITEMS.indexOf(it))
            }
            builder.setNegativeButton(R.string.cancel) { dialog, which ->

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }


        fadapter = FavoriteItemAdapter(favoriteItemList, onFavClick)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = fadapter
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}