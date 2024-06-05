package com.project.nyacawa.presentation.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.project.nyacawa.R
import com.project.nyacawa.data.User
import com.project.nyacawa.data.UserCache
import com.project.nyacawa.data.UserDataCache
import com.project.nyacawa.databinding.FragmentProfileBinding
import com.project.nyacawa.domain.logic.ProfileButtonStates
import com.project.nyacawa.domain.logic.UserViewModel
import com.project.nyacawa.presentation.ui.MainActivity.Companion.USER_DATA


class Profile : Fragment(){

    private lateinit var binding: FragmentProfileBinding
    private lateinit var userCash: UserDataCache
    private val userViewModel: UserViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        userCash = UserDataCache(requireContext())
        val navController = findNavController()

        binding.accountField.setState(ProfileButtonStates.REGISTER)
        binding.accountField.setHandlers(
            goToAuthorization = {navController.navigate(R.id.profile_goToRegistration) },
            goToRegistration = {navController.navigate(R.id.profile_goToAuthorization) },
            accountExit = {
                userCash.clearUserCache()
                navController.popBackStack(R.id.mainMenu, false)
                navController.navigate(R.id.authorization)

            }
        )

        binding.wishList.setOnClickListener {
            findNavController().navigate(R.id.goToFavorite)
        }

        binding.donatButton.setOnClickListener {
            val url = "https://send.monobank.ua/jar/7XUKjCTh1y"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        if(!userViewModel.user.isInitialized){
            userViewModel.getUserById(userCash.getUserCache().userId){
                if(it!=null){
                    binding.accountField.setUser(it)
                    Log.d("[USER PROFILE]", "Get data from API ${it.name}")
                }
            }
        }else{
            Log.d("[USER PROFILE]", "User name ${userViewModel.user.value?.name}")
            binding.accountField.setUser(userViewModel.user.value!!)
        }

        binding.bonusCount.text = userViewModel.user.value?.bonusValue.toString()

        return binding.root
    }

}