package com.example.practiseDemo.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practiseDemo.apiData.Item

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user: List<Item>)

    @Query("SELECT * FROM users")
     fun getData():PagingSource<Int, Item>

    @Query("DELETE FROM users")
    suspend fun clearAll()
}