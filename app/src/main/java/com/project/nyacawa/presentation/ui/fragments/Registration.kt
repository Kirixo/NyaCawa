package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.media3.common.util.Util
import androidx.navigation.fragment.findNavController
import com.project.nyacawa.R
import com.project.nyacawa.data.UserCache
import com.project.nyacawa.data.UserDataCache
import com.project.nyacawa.databinding.FragmentRegistrationBinding
import com.project.nyacawa.domain.logic.UserViewModel
import com.project.nyacawa.domain.logic.Errors

class Registration : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    private val userViewModel: UserViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val navController = findNavController()
        val cache: UserDataCache = UserDataCache(requireContext())

        binding.alreadyHaveAccount.setOnClickListener {
            navController.navigate(R.id.registration_goToAuthorization)
        }

        binding.registrationButton.setOnClickListener {
            if (binding.repeatPassword.getText() == binding.password.getText()) {
                cache.clearUserCache()
                userViewModel.registerUser(binding.password.getText(), binding.loginInput.getText(), binding.nicknameInput.getText())
            } else {
                Toast.makeText(requireContext(), getString(R.string.pasword_not_same), Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}
