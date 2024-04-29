package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.nyacawa.R
import com.project.nyacawa.domain.logic.ToolBarState
import com.project.nyacawa.domain.logic.ToolBarTypes


class Authorization : Fragment(), ToolBarState {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_authorization, container, false)
    }

    override fun getToolBarState(): ToolBarTypes {
        return ToolBarTypes.BACK_WITH_ACCOUNT
    }

    override fun getFragmentName(): String {
        return getString(R.string.authorisation)
    }

}