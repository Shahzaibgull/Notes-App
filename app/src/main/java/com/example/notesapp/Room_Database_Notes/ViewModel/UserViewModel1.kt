package com.example.notesapp.Room_Database_Notes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class UserViewModel1(private val context: Context):ViewModel() {

    val user1ListLiveData: LiveData<List<User1>> = UserRepository1.getAllUserData(context)

    fun insert1(context: Context, user1: User1) {
        UserRepository1.insert(context, user1)
    }

    fun getAllUserData(context: Context): LiveData<List<User1>>?
    {
        return UserRepository1.getAllUserData(context)
    }

    fun updateUser(context: Context, user1: User1) {
        UserRepository1.updateUser(context, user1)
    }

    fun deleteUser(context: Context, user1: User1) {
        UserRepository1.deleteUser(context, user1)
    }

}