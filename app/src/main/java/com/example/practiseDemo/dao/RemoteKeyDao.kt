package com.example.practiseDemo.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey:List<RemoteKey>)


    @Query("SELECT * FROM remote_keys WHERE id=:id")
    suspend fun remoteKeyByQuery(id:Int?): RemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}