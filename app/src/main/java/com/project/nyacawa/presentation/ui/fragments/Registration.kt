package com.project.nyacawa.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import com.project.nyacawa.R
import com.project.nyacawa.databinding.FragmentMainMenuBinding
import com.project.nyacawa.domain.logic.ToolBarState
import com.project.nyacawa.domain.logic.ToolBarTypes


class Registration : Fragment(), ToolBarState {

    private lateinit var binding: FragmentMainMenuBinding

    private lateinit var label : TextView
    private lateinit var list : ScrollView
    private lateinit var button : AppCompatImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        label = binding.thisSesonBar.findViewById<TextView>(R.id.scroll_bar_name)
        list = binding.thisSesonBar.findViewById<ScrollView>(R.id.anime_list)
        button = binding.thisSesonBar.findViewById<AppCompatImageButton>(R.id.button)

    }

    override fun getToolBarState(): ToolBarTypes {
        return ToolBarTypes.BACK_WITH_ACCOUNT
    }

    override fun getFragmentName(): String {
        return getString(R.string.registration)
    }


}