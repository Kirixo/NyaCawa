package com.project.nyacawa.data

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable


//ANIME DATA CLASS

data class AnimeData(
    val aired_end: Int,
    val aired_start: Int,
    val description: String?,
    val general_score: Float,
    val id: Long,
    val image: String?,
    val name: String?,
    val total_episodes: Int
)
: Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(aired_end)
        parcel.writeInt(aired_start)
        parcel.writeString(description)
        parcel.writeFloat(general_score)
        parcel.writeLong(id)
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeInt(total_episodes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimeData> {
        override fun createFromParcel(parcel: Parcel): AnimeData {
            return AnimeData(parcel)
        }

        override fun newArray(size: Int): Array<AnimeData?> {
            return arrayOfNulls(size)
        }
    }

}