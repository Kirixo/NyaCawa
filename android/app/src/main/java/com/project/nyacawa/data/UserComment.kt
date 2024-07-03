package com.project.nyacawa.data

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class UserComment(
    val icon: Bitmap?,
    val status: String?,
    val name: String?
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Bitmap::class.java.classLoader),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(icon, flags)
        parcel.writeString(status)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserComment> {
        override fun createFromParcel(parcel: Parcel): UserComment {
            return UserComment(parcel)
        }

        override fun newArray(size: Int): Array<UserComment?> {
            return arrayOfNulls(size)
        }
    }

}