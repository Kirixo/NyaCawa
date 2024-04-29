package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.nyacawa.R
import com.project.nyacawa.domain.logic.ToolBarState
import com.project.nyacawa.domain.logic.ToolBarTypes

class Profile : Fragment(), ToolBarState {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun getToolBarState(): ToolBarTypes {
        return  ToolBarTypes.BACK
    }

    override fun getFragmentName(): String {
        return getString(R.string.profile)
    }
}