package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.project.nyacawa.R
import com.project.nyacawa.databinding.FragmentProfileBinding
import com.project.nyacawa.domain.logic.ProfileButtonStates

class Profile : Fragment(){

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val navController = findNavController()

        binding.accountField.setState(ProfileButtonStates.REGISTER)
        binding.accountField.setHandlers(
            goToAuthorization = {navController.navigate(R.id.profile_goToRegistration)},
            goToRegistration = {navController.navigate(R.id.profile_goToAuthorization)},
            accountExit = { TODO("Account exit method") }
        )

        return binding.root
    }
}