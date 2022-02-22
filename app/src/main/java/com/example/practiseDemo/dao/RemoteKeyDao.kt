package com.example.practiseDemo.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practiseDemo.apiData.Item
@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey:List<Item>)

    @Query("SELECT * FROM remote_keys WHERE id=:id")
    suspend fun remoteKeyByQuery(id:Int?): RemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun deleteByQuery()
}