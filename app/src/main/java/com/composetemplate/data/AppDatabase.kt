package com.composetemplate.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.composetemplate.data.dao.UserDao
import com.composetemplate.data.entity.User

@Database(
    version = 1,
    entities = [User::class],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}
