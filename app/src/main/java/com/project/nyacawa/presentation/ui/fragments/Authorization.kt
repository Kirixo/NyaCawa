package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.nyacawa.R
import com.project.nyacawa.data.UserCache
import com.project.nyacawa.data.UserDataCache
import com.project.nyacawa.databinding.FragmentAuthorizationBinding
import com.project.nyacawa.domain.logic.UserViewModel


class Authorization : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.dontHaveAccount.setOnClickListener {
            navController.navigate(R.id.authorization_goToRegistration)
        }

        binding.authButton.setOnClickListener {
            userViewModel.authUserData(binding.password.getText(), binding.loginInput.getText())
        }

    }
}