package com.project.nyacawa.domain.logic

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
    private val gson = Gson()

    private val _catalogAnimeList = MutableLiveData<List<AnimeData>>()
    private val _todayAnimeList = MutableLiveData<List<AnimeData>>()
    private val _topAnimeList = MutableLiveData<List<AnimeData>>()
    private val _thisSeasonAnimeList = MutableLiveData<List<AnimeData>>()

    val catalogAnimeList: LiveData<List<AnimeData>> get() = _catalogAnimeList
    val todayAnimeList: LiveData<List<AnimeData>> get() = _todayAnimeList
    val topAnimeList: LiveData<List<AnimeData>> get() = _topAnimeList
    val thisSeasonAnimeList: LiveData<List<AnimeData>> get() = _thisSeasonAnimeList

    init {
        getCatalogAnimeList()
    }

    fun getCatalogAnimeList() {
        fetchAndPostAnimeList({ api.fetchAnimeList(it) }, _catalogAnimeList)
    }

    fun getTodayList() {
        fetchAndPostAnimeList({ api.fetchTodayAnimeList(it) }, _todayAnimeList)
    }

    fun getSeasonList() {
        fetchAndPostAnimeList({ api.fetchSeasonAnimeList(it) }, _thisSeasonAnimeList)
    }

    fun getTopList() {
        fetchAndPostAnimeList({ api.fetchTopAnimeList(it) }, _topAnimeList)
    }

    private fun fetchAndPostAnimeList(apiCall: (callback: (String?) -> Unit) -> Unit, liveData: MutableLiveData<List<AnimeData>>) {
        apiCall { response ->
            val animeList = parseAnimeList(response)
            liveData.postValue(animeList)
        }
    }

    private fun parseAnimeList(response: String?): List<AnimeData> {
        return if (response != null) {
            val jsonObject = gson.fromJson(response, JsonObject::class.java)
            val listOfAnimeJson = jsonObject.getAsJsonArray("listOfAnime")
            val typeToken = object : TypeToken<List<AnimeData>>() {}.type
            gson.fromJson(listOfAnimeJson, typeToken)
        } else {
            emptyList()
        }
    }
}
