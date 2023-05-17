package com.composetemplate.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.composetemplate.data.entity.User

@Dao
interface UserDao {
    @Upsert
    fun insertOrUpdateUser(user: User)

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getUser(userId: Long): User
}
