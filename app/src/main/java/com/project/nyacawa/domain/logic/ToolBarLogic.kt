package com.project.nyacawa.domain.logic

import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.project.nyacawa.R

interface ToolBarState{
    fun getToolBarState() : ToolBarTypes
    fun getFragmentName() : String
}
