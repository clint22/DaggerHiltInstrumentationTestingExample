package com.clintpauldev.daggerhiltinstrumentationtestingexample.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * from user")
    fun observeAllUsers(): LiveData<List<User>>

    @Query("SELECT * from user where age = :age")
    fun observeUserByAge(age: Int): LiveData<User>
}


