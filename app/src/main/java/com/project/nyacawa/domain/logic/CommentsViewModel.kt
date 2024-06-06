package com.project.nyacawa.domain.logic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.data.Comment
import com.project.nyacawa.data.User
import com.project.nyacawa.data.nyacawaapi.NyaCawaApi

class CommentsViewModel: ViewModel() {
    private val api = NyaCawaApi()
    private val gson = Gson()

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: MutableLiveData<List<Comment>> get() = _comments

    fun sendComment(text: String, userId: Int, animeId: Int){
        val jsonObject = JsonObject().apply {
            addProperty("user_id", userId)
            addProperty("text", text)
            addProperty("anime_id", animeId)
        }
        val gson = Gson()
        val body = gson.toJson(jsonObject)

        api.sendNewCommentList(body){
            Log.d("[COMMENT DATA]", "REGISTERED: $body")
            if(it!=null){
                try {
                    Log.d("[COMMENT DATA]", "Received msg: $it")
                    val data = gson.fromJson(it, JsonObject::class.java)
                    val msg = data.get("message").asInt
                    if(msg == 0){
                        getComments(animeId)
                    }
                } catch (e: Exception) {
                    Log.e("[VALID USER DATA]", "Error parsing response data", e)
                }
            }
        }
    }

    fun getComments(animeId: Int){
        api.fetchCommentList(animeId){ data->
            Log.d("[COMMENT DATA]", "Received data: $data animeId: $animeId")
            if (data != null) {
                val jsonObject = gson.fromJson(data, JsonObject::class.java)
                if(jsonObject!= null){
                    val infoData = jsonObject.getAsJsonArray("listOfComments")
                    val typeToken = object : TypeToken<List<Comment>>() {}.type
                    val user = gson.fromJson<List<Comment>>(infoData, typeToken)
                    Log.d("[COMMENT DATA]", "Received data: size: ${user.size}")
                    comments.postValue(user)
                }else{
                    comments.postValue(emptyList())
                }
            }
        }
    }
}