package com.project.nyacawa.data

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable


//ANIME DATA CLASS

class AnimeData(
    val name: String,
    val poster: Bitmap?,
    val description: String,
    val id: Long,
    val episodeCount: Int,
    val rating: Float
): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readParcelable<Bitmap>(Bitmap::class.java.classLoader),
        parcel.readString().orEmpty(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeParcelable(poster, flags)
        parcel.writeString(description)
        parcel.writeLong(id)
        parcel.writeInt(episodeCount)
        parcel.writeFloat(rating)
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