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

class UserViewModel: ViewModel() {
    private val api: NyaCawaApi = NyaCawaApi()
    private val _user: MutableLiveData<User> = MutableLiveData<User>()
    val user: MutableLiveData<User> get() = _user

    fun getUserById(id: Int, callback: (User?) -> Unit) {
        api.fetchUserInfo(id) { userData ->
            if(userData != null){
                val gson = Gson()
                val jsonObject = gson.fromJson(userData, JsonObject::class.java)
                val typeToken = object : TypeToken<User>() {}.type
                val user = gson.fromJson<User>(jsonObject, typeToken)
                callback(user)
            } else {
                callback(null)
            }
        }
    }

    fun validData(md5Password: String, login: String){
        val jsonObject = JsonObject()
        jsonObject.addProperty("email", login)
        jsonObject.addProperty("password", md5Password)
        val gson = Gson()
        val body = gson.toJson(jsonObject)
        api.sendUserData(body) { responseData ->
            Log.d("[VALID USER DATA]", "validData: $body")
            Log.d("[VALID USER DATA]", "validData received data: $responseData")



//            _user.postValue()
        }
    }
}