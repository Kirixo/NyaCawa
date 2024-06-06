package com.project.nyacawa.data.nyacawaapi

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.project.nyacawa.data.AnimeData
import com.project.nyacawa.data.User
import okhttp3.*
import java.io.IOException
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class NyaCawaApi {

    private val client = OkHttpClient()
    private val baseUrl = "https://nyacawa.yooud.org"

    fun fetchAnimeList(callback: (String?) -> Unit) {
        val url = "$baseUrl/api/animelist"
        makeRequest(url, null, callback)
    }

    fun fetchTopAnimeList(callback: (String?) -> Unit) {
        val url = "$baseUrl/api/topanimelist"
        makeRequest(url, null, callback)
    }
    fun fetchTodayAnimeList(callback: (String?) -> Unit) {
        val url = "$baseUrl/api/todayanimelist"
        makeRequest(url, null, callback)
    }
    fun fetchSeasonAnimeList(callback: (String?) -> Unit) {
        val url = "$baseUrl/api/seasonanimelist"
        makeRequest(url, null, callback)
    }

    fun fetchUserInfo(userId: Int, callback: (String?) -> Unit) {
        val url = "$baseUrl/api/profileinfo?id=$userId"
        makeRequest(url, null, callback)
    }

    fun fetchFavoriteList(userId: Int, callback: (String?) -> Unit) {
        val url = "$baseUrl/api/favorites?id=$userId"
        makeRequest(url, null, callback)
    }



    fun fetchAnimeInfo(animeId: String, callback: (String?) -> Unit) {
        val url = "$baseUrl/anime/$animeId"
        makeRequest(url, null, callback)
    }
    fun sendRegisterData(json: String?, callback: (String?) -> Unit) {
        val url = "$baseUrl/api/reqister"
        val body = json?.let { RequestBody.create(MediaType.parse("application/json; charset=utf-8"), it) }
        makeRequest(url, body, callback)

    }

    fun sendUserData(json: String?, callback: (String?) -> Unit) {
        val url = "$baseUrl/api/login"
        val body = json?.let { RequestBody.create(MediaType.parse("application/json; charset=utf-8"), it) }
        makeRequest(url, body, callback)
    }

    private fun makeRequest(url: String, body: RequestBody?, callback: (String?) -> Unit) {
        val requestBuilder = Request.Builder()
            .url(url)
        if (body != null) {
            requestBuilder.post(body)
        }
        val request = requestBuilder.build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body()?.string()
                callback(responseData)
            }
        })
    }


}