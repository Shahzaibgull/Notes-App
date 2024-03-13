package com.example.notesapp.Room_Database_Notes

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="user")
data class User1(var title: String, var details: String?) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString()
    ) {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(details)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User1> {
        override fun createFromParcel(parcel: Parcel): User1 {
            return User1(parcel)
        }

        override fun newArray(size: Int): Array<User1?> {
            return arrayOfNulls(size)
        }
    }
     //Serializable

}