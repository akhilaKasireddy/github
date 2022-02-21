package com.example.practisedemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practisedemo.apiData.Item
import com.example.practisedemo.dao.RemoteKey
import com.example.practisedemo.dao.RemoteKeyDao
import com.example.practisedemo.dao.UserDao

@Database(entities = [Item::class, RemoteKey::class], version = 2, exportSchema = false)
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