package com.project.nyacawa.domain.logic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.data.nyacawaapi.NyaCawaApi


class CatalogAnimeListViewModel : ViewModel() {
    private val api = NyaCawaApi()
    private val _animeList = MutableLiveData<List<AnimeData>>()
    val animeList: LiveData<List<AnimeData>> get() = _animeList

    init {
        fetchAnimeList()
    }

    private fun fetchAnimeList() {
        api.fetchAnimeList {
            if (it != null) {
                val gson = Gson()
                val jsonObject = gson.fromJson(it, JsonObject::class.java)
                val listOfAnimeJson = jsonObject.getAsJsonArray("listOfAnime")
                val typeToken = object : TypeToken<List<AnimeData>>() {}.type
                val animeList: List<AnimeData> = gson.fromJson(listOfAnimeJson, typeToken)
                _animeList.postValue(animeList)
            } else {
                _animeList.postValue(emptyList())
            }
        }
    }
}