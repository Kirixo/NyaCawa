package com.project.nyacawa.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.nyacawa.R
import com.project.nyacawa.databinding.ViewInputFieldBinding

class EditTextField(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private val binding: ViewInputFieldBinding;

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_input_field, this, true)
        binding = ViewInputFieldBinding.bind(this);
        attributeInit(attrs, defStyleAttr, defStyleRes)
    }

    private fun attributeInit(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.EditTextField,
            defStyleAttr,
            defStyleRes
        )

        val label = typedArray.getText(R.styleable.EditTextField_label)
        setLabel(label);

        typedArray.recycle();
    }

    private fun setLabel(text: CharSequence?) {
        binding.label.text = text
    }
}

