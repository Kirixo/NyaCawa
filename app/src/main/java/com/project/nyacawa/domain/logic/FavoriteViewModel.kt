package com.project.nyacawa.domain.logic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.data.FavoriteItem
import com.project.nyacawa.data.nyacawaapi.NyaCawaApi

class FavoriteViewModel  : ViewModel()  {
    private val api = NyaCawaApi()
    private val gson = Gson()

    private val _favoriteList = MutableLiveData<List<FavoriteItem>>()
    val favoriteList: MutableLiveData<List<FavoriteItem>> get() = _favoriteList

    fun getFavoriteList(userId: Int) {
        api.fetchFavoriteList(userId) { response ->
            Log.d("[FAVORITE LIST]", "getFavoriteList: $response")
            response?.let {
                try {
                    val jsonObject = gson.fromJson(it, JsonObject::class.java)
                    val listOfFavoritesJson = jsonObject.getAsJsonArray("listOfFavorites")
                    val typeToken = object : TypeToken<List<FavoriteItem>>() {}.type
                    val list = gson.fromJson<List<FavoriteItem>>(listOfFavoritesJson, typeToken)
                    Log.d("[FAVORITE LIST]", "getFavoriteList: list size: ${list.size}")
                    _favoriteList.postValue(list)
                } catch (e: Exception) {
                    Log.e("[FAVORITE LIST]", "Error parsing favorite list", e)
                    _favoriteList.postValue(emptyList())
                }
            } ?: run {
                Log.e("[FAVORITE LIST]", "Response is null")
                _favoriteList.postValue(emptyList())
            }
        }
    }
}