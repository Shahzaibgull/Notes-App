package com.example.notesapp.Room_Database_Notes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao1 {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user1: User1)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalance(user1: User1)

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAllUserData(): LiveData<List<User1>>

    @Update
    suspend fun updateUser(user1: User1)

    @Query("UPDATE user SET title=:firstName, details=:lastName")
    suspend fun update(firstName: String, lastName: String)

    @Delete
    suspend fun deleteUser(user1: User1)
}