package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.nyacawa.R
import com.project.nyacawa.databinding.FragmentMainMenuBinding
import com.project.nyacawa.domain.logic.ToolBarState
import com.project.nyacawa.domain.logic.ToolBarTypes


class MainMenu : Fragment(), ToolBarState {

    private lateinit var binding: FragmentMainMenuBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun getToolBarState(): ToolBarTypes {
        return ToolBarTypes.SEARCH
    }

    override fun getFragmentName(): String {
        return "Main Menu"
    }

}