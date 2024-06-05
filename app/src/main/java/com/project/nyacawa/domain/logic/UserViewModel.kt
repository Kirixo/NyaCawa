package com.project.nyacawa.domain.logic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.data.User
import com.project.nyacawa.data.nyacawaapi.NyaCawaApi


data class ResponseData(
    val id: Int,
    val token: String
)
data class ResponseUserData(
    val bonus_points: String,
    val name: String,
    val status: String
)

class UserViewModel: ViewModel() {
    private val api: NyaCawaApi = NyaCawaApi()
    private val _user: MutableLiveData<User> = MutableLiveData<User>()
    val user: MutableLiveData<User> get() = _user

    fun setUser(user: User){
        Log.d("[USER DATA]", "setUser: with name ${user.name}")
        _user.postValue(user)
    }

    fun getUserById(id: Int, callback: (User?) -> Unit) {
        api.fetchUserInfo(id) { userData ->
            Log.d("[USER DATA]", "Received data: $userData")
            if (userData != null) {
                val gson = Gson()
                val jsonObject = gson.fromJson(userData, JsonObject::class.java)
                val infoData = jsonObject.getAsJsonObject("info")
                val typeToken = object : TypeToken<ResponseUserData>() {}.type
                val user = gson.fromJson<ResponseUserData>(infoData, typeToken)
                Log.d("[USER DATA]", "Received data: name = ${user.name}")

                callback(User(id, user.name, user.bonus_points.toInt(), user.status))
            } else {
                callback(null)
            }
        }
    }


    fun validData(md5Password: String, login: String) {
        val jsonObject = JsonObject().apply {
            addProperty("email", login)
            addProperty("password", md5Password)
        }
        val gson = Gson()
        val body = gson.toJson(jsonObject)
        api.sendUserData(body) { responseData ->
            Log.d("[VALID USER DATA]", "validData: $body")
            Log.d("[VALID USER DATA]", "validData received data: $responseData")

            if (responseData != null) {
                try {
                    val data = gson.fromJson(responseData, JsonObject::class.java)
                    val loginData = data.getAsJsonObject("loginData")
                    val typeToken = object : TypeToken<ResponseData>() {}.type
                    val userResponse = gson.fromJson<ResponseData>(loginData, typeToken)
                    Log.d("[VALID USER DATA]", "validData received user id: ${userResponse.id}")

                    getUserById(userResponse.id) {
                        _user.postValue(it)
                    }
                } catch (e: Exception) {
                    Log.e("[VALID USER DATA]", "Error parsing response data", e)
                }
            } else {
                Log.e("[VALID USER DATA]", "Response data is null")
            }
        }
    }
}