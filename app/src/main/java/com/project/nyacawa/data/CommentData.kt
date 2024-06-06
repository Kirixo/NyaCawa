package com.project.nyacawa.data

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

data class Comment(
    val id: Int,
    val username: String,
    val status: String,
    val text: String
)