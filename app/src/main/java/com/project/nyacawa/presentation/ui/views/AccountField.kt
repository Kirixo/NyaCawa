package com.project.nyacawa.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.nyacawa.R
import com.project.nyacawa.data.Comment
import com.project.nyacawa.data.User
import com.project.nyacawa.data.UserComment
import com.project.nyacawa.databinding.ViewAccountBinding
import com.project.nyacawa.domain.adapters.comment.onShareCommentClick
import com.project.nyacawa.domain.logic.ProfileButtonStates


class AccountField(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var goToAuthorization: GoToAuthorization
    private lateinit var goToRegistration: GoToRegistration
    private lateinit var accountExit: AccountExit

    private var state: ProfileButtonStates = ProfileButtonStates.REGISTER
    private lateinit var user: UserComment
    private val binding : ViewAccountBinding

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)


    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_account, this, true)
        binding = ViewAccountBinding.bind(this)
        attributeInit(attrs, defStyleAttr, defStyleRes)
    }

    private fun attributeInit(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int){
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AccountField, defStyleAttr, defStyleRes)

        val drawable = typedArray.getDrawable(R.styleable.AccountField_buttonIcon)
        binding.actionButton.setImageDrawable(drawable);

        typedArray.recycle();
    }


    fun setHandlers(goToAuthorization: GoToAuthorization, goToRegistration: GoToRegistration, accountExit: AccountExit){
        this.goToAuthorization = goToAuthorization
        this.goToRegistration = goToRegistration
        this.accountExit = accountExit
    }

    private fun updateProfileButtonClickListener(){
        binding.actionButton.setOnClickListener {
            when(this.state){
                ProfileButtonStates.EXIT -> accountExit.invoke()
                ProfileButtonStates.AUTHORIZATION -> goToAuthorization.invoke()
                ProfileButtonStates.REGISTER -> goToRegistration.invoke()
                else -> Unit
            }
        }
    }

    fun setState(state: ProfileButtonStates){
        this.state = state
        when (state){
            ProfileButtonStates.EXIT -> setExitButton()
            ProfileButtonStates.AUTHORIZATION -> setLogInButton()
            ProfileButtonStates.REGISTER -> setLogInButton()
            else -> Unit
        }
        updateProfileButtonClickListener()
    }

    private fun setExitButton(){
        binding.actionButton.setImageDrawable(getDrawable(context, R.drawable.log_out_ico))
    }
    private fun setLogInButton(){
        binding.actionButton.setImageDrawable(getDrawable(context, R.drawable.log_in_ico))
    }
    fun setShareButton(onShareCommentClick: onShareCommentClick, comment: Comment){
        binding.actionButton.setOnClickListener {
            onShareCommentClick.invoke(comment)
        }
        setState(ProfileButtonStates.SHARE)
    }
    fun setProfile(user: UserComment, state: ProfileButtonStates){
        this.user = user
        setState(state)
    }
}