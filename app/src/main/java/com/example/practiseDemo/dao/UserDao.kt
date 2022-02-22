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
    suspend fun insertAll(user_Table: List<Item>)

    @Query("SELECT * FROM user_Table WHERE name=:name")
     fun pagingSource(name: String):PagingSource<Int, Item>

    @Query("DELETE FROM user_Table")
    suspend fun clearAll()
}