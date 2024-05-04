package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.project.nyacawa.R
import com.project.nyacawa.databinding.FragmentAuthorizationBinding


class Authorization : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        val navController = findNavController()


        binding.dontHaveAccount.setOnClickListener{
            navController.navigate(R.id.authorization_goToRegistration)
        }


        return binding.root
    }


}