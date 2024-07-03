package com.project.nyacawa.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText


class ExtendedEditText: TextInputEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            clearFocus()
        }
        return super.onKeyPreIme(keyCode, event)
    }

}