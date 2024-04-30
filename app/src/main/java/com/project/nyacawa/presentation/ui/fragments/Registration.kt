package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.project.nyacawa.R
import com.project.nyacawa.databinding.FragmentRegistrationBinding


class Registration : Fragment(){

    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val navController = findNavController()

        binding.alreadyHaveAccount.setOnClickListener{
            navController.navigate(R.id.registration_goToAuthorization)
        }

        return binding.root
    }


}