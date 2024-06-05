package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.nyacawa.R
import com.project.nyacawa.data.UserCache
import com.project.nyacawa.data.UserDataCache
import com.project.nyacawa.databinding.FragmentRegistrationBinding
import com.project.nyacawa.domain.logic.UserViewModel


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
            if (binding.repeatPassword.getText().toString() == binding.password.getText().toString()) {
                userViewModel.registerUser(binding.password.getText().toString(), binding.loginInput.getText().toString(), binding.nicknameInput.getText().toString())

                userViewModel.user.observe(requireActivity()){ responseData ->
                    requireActivity().runOnUiThread {
                        if (responseData != null) {
                            val userCache = UserCache(responseData.id, -1)
                            Log.d("[REGISTER]", "User id reg: ${responseData.id}")
                            cache.saveUserCache(userCache)
                            findNavController().navigate(R.id.goToMainMenu)
                        } else {
                            Toast.makeText(requireContext(), getString(R.string.nickname_or_login_already_exist), Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            } else {
                Toast.makeText(requireContext(), getString(R.string.pasword_not_same), Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}
