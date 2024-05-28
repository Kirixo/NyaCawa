package com.project.nyacawa.data

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

data class Comment(
    val id: Int,
    val profile: Profile?,
    val text: String,
    val likes: Int,
    val dislikes: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(Profile::class.java.classLoader),
        parcel.readString().orEmpty(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(profile?.let { null }, flags)
        parcel.writeString(text)
        parcel.writeInt(likes)
        parcel.writeInt(dislikes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }

    fun getRating(): Int{
        return likes - dislikes
    }

    fun getRatingColor () :Int = if(getRating() <= 0){
        Color.parseColor("#B17070")
    }else{
        Color.parseColor("#87AE7E")
    }
}