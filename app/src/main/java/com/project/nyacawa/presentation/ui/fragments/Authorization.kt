package com.project.nyacawa.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.project.nyacawa.R
import com.project.nyacawa.data.UserCache
import com.project.nyacawa.data.UserDataCache
import com.project.nyacawa.databinding.FragmentAuthorizationBinding
import com.project.nyacawa.domain.logic.UserViewModel
import java.math.BigInteger
import java.security.MessageDigest


class Authorization : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        val navController = findNavController()
        val cache: UserDataCache = UserDataCache(requireContext())

        binding.dontHaveAccount.setOnClickListener{
            navController.navigate(R.id.authorization_goToRegistration)
        }

        binding.authButton.setOnClickListener{
            userViewModel.validData(md5(binding.password.getText()), binding.loginInput.getText())
            userViewModel.user.removeObserver{
                val userCache: UserCache = UserCache(it.id, -1)
                cache.saveUserCache(userCache)
            }
        }


        return binding.root
    }

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val messageDigest = md.digest(input.toByteArray())
        val no = BigInteger(1, messageDigest)
        var hashText = no.toString(16)
        while (hashText.length < 32) {
            hashText = "0$hashText"
        }
        return hashText
    }


}