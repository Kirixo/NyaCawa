package com.project.nyacawa.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.nyacawa.R
import com.project.nyacawa.databinding.InputFieldBinding

class EditTextField(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private val binding : InputFieldBinding;

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.input_field, this, true)
        binding = InputFieldBinding.bind(this);
        attributeInit(attrs, defStyleAttr, defStyleRes)
    }

    private fun attributeInit(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int){
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextField, defStyleAttr, defStyleRes)

        val text = typedArray.getText(R.styleable.EditTextField_text)
        setText(text);

        val label = typedArray.getText(R.styleable.EditTextField_label)
        setLabel(label);

        typedArray.recycle();
    }

    private fun setText(text: CharSequence?) {
        binding.inputText.setText(text)
    }

    fun setLabel(text: CharSequence?){
        binding.label.text = text
    }

    fun getData(): String {
        return binding.inputText.text.toString();
    }

}

