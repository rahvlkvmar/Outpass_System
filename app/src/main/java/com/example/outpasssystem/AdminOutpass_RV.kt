package com.example.outpasssystem

import android.os.Parcel
import android.os.Parcelable

data class AdminOutpassRV(val sno: String? = null, val name: String? = null, val rollnum: String? = null,
                           val hostel: String? = null, val roomNum: String? = null,
                           val contactNum: String? = null, val parent: String? = null,
                           val parentNum : String? = null, val leave: String? = null,
                           val arrive: String? = null, val transport : String? = null,
                            val purpose: String? = null, val uid: String? = null) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(sno)
        parcel.writeString(name)
        parcel.writeString(rollnum)
        parcel.writeString(hostel)
        parcel.writeString(roomNum)
        parcel.writeString(contactNum)
        parcel.writeString(parent)
        parcel.writeString(parentNum)
        parcel.writeString(leave)
        parcel.writeString(arrive)
        parcel.writeString(transport)
        parcel.writeString(purpose)
        parcel.writeString(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AdminOutpassRV> {
        override fun createFromParcel(parcel: Parcel): AdminOutpassRV {
            return AdminOutpassRV(parcel)
        }

        override fun newArray(size: Int): Array<AdminOutpassRV?> {
            return arrayOfNulls(size)
        }
    }

}
