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
import androidx.fragment.app.activityViewModels
import com.project.nyacawa.R
import com.project.nyacawa.data.FavoriteItem
import com.project.nyacawa.data.User
import com.project.nyacawa.domain.adapters.favorite.FavoriteItemAdapter
import com.project.nyacawa.domain.adapters.favorite.onHeartClick
import com.project.nyacawa.domain.logic.FavoriteViewModel
import com.project.nyacawa.domain.logic.UserViewModel

/**
 * A fragment representing a list of Items.
 */
class FavoriteFragment : Fragment() {

    private var columnCount = 2
    private lateinit var fadapter: FavoriteItemAdapter
    private val favoriteList: FavoriteViewModel by activityViewModels()

    private val userViewModel: UserViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_list, container, false)
        var favoriteItemList: List<FavoriteItem> = emptyList()

        val onFavClick: onHeartClick = {
            val builder = AlertDialog.Builder(requireContext())

            builder.setMessage(getString(R.string.deletion_request))

            builder.setPositiveButton(getString(R.string.yes)) { _, _ ->

            }
            builder.setNegativeButton(R.string.cancel) { _, _ ->

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        fadapter = FavoriteItemAdapter(favoriteItemList, onFavClick)

        if(userViewModel.user.isInitialized){
            favoriteList.getFavoriteList(userViewModel.user.value!!.id)
        }


        favoriteList.favoriteList.observe(requireActivity()){
            fadapter.updateItems(it)
        }



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

}