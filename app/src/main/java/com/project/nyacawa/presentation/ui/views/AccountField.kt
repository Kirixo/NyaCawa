package com.project.nyacawa.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.nyacawa.R
import com.project.nyacawa.databinding.ViewAccountBinding

class AccountField(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)
    private val binding : ViewAccountBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_account, this, true)
        binding = ViewAccountBinding.bind(this)
    }


}