package com.example.notesapp.Room_Database_Notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User1::class], version = 1, exportSchema = false)
abstract class UserDatabase1 : RoomDatabase(){

    abstract fun getDao(): UserDao1

    companion object{

        private const val DATABASE_NAME="UserDatabase"
        @Volatile
        var instance: UserDatabase1?=null

        fun getInstance(context: Context): UserDatabase1?{
            if (instance==null){
                synchronized(UserDatabase1::class.java)
                {
                    if (instance==null)
                    {
                        instance= Room.databaseBuilder(
                            context,
                            UserDatabase1::class.java,
                            DATABASE_NAME).build()
                    }
                }
            }
            return instance
        }
    }
}