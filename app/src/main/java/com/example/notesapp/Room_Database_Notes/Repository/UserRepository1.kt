package com.example.notesapp.Room_Database_Notes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class UserRepository1 {

    companion object {
        private var userDatabase1: UserDatabase1? = null
        fun initaliseDB(context: Context): UserDatabase1? {
            return UserDatabase1.getInstance(context)
        }

        fun insert(context: Context, user1: User1) {
            userDatabase1 = initaliseDB(context)
            CoroutineScope(IO).launch {

                userDatabase1?.getDao()?.insert(user1)
            }
        }

        fun getAllUserData(context: Context): LiveData<List<User1>> {
            userDatabase1 = initaliseDB(context)
            return userDatabase1?.getDao()?.getAllUserData() ?: MutableLiveData()
        }

        fun updateUser(context: Context, user1: User1) {
            userDatabase1 = initaliseDB(context)
            CoroutineScope(IO).launch {
                userDatabase1?.getDao()?.updateUser(user1)
            }
        }

        fun deleteUser(context: Context, user1: User1) {
            userDatabase1 = initaliseDB(context)
            CoroutineScope(IO).launch {
                userDatabase1?.getDao()?.deleteUser(user1)
            }

        }
    }
}