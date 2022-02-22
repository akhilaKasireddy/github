package com.example.practiseDemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practiseDemo.apiData.Item
import com.example.practiseDemo.dao.RemoteKey
import com.example.practiseDemo.dao.RemoteKeyDao
import com.example.practiseDemo.dao.UserDao
import com.example.practiseDemo.dao.UserTable

@Database(entities = [Item::class,RemoteKey::class,UserTable::class], version = 2, exportSchema = false)
abstract class RoomDbApp : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {

        private var instance: RoomDbApp? = null

        fun getInstance(context: Context): RoomDbApp {

            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDbApp::class.java, "DbApp"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            return instance!!
        }

    }

}