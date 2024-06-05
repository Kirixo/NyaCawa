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
//    private val baseUrl = "https://172.161.138.138"

    fun fetchAnimeList(callback: (String?) -> Unit) {
        val url = "$baseUrl/api/animelist"
        makeRequest(url, callback)
    }

    fun fetchUserInfo(userId: Int, callback: (String?) -> Unit) {
        val url = "$baseUrl/user/$userId"
        makeRequest(url, callback)
    }

    fun fetchAnimeInfo(animeId: String, callback: (String?) -> Unit) {
        val url = "$baseUrl/anime/$animeId"
        makeRequest(url, callback)
    }

    private fun makeRequest(url: String, callback: (String?) -> Unit) {
        val request = Request.Builder()
            .url(url)
            .build()

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