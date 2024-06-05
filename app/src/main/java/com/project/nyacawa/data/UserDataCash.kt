package com.project.nyacawa.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

data class UserCache(
    val userId: Int,
    val lastAnimeId: Int
){
    fun isUserCashed(): Boolean{
        return (userId >= 0)
    }
}

class UserDataCache(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "user_data_cache"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_LAST_ANIME_ID = "last_anime_id"
    }

    fun saveUserCache(userCache: UserCache) {
        with(sharedPreferences.edit()) {
            putInt(KEY_USER_ID, userCache.userId)
            putInt(KEY_LAST_ANIME_ID, userCache.lastAnimeId)
            apply()
        }
    }

    fun getUserCache(): UserCache {
        val userId = sharedPreferences.getInt(KEY_USER_ID, -1)
        val lastAnimeId = sharedPreferences.getInt(KEY_LAST_ANIME_ID, -1)

        Log.d("[USER DATA]", "Load from cash ${userId} ")
        return UserCache(userId, lastAnimeId)
    }

    fun clearUserCache() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}
