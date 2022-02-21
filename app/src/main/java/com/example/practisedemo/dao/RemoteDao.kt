package com.example.practisedemo.dao

import androidx.room.*
import com.example.practisedemo.apiData.Item

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: List<Item>)

    @Query("SELECT * FROM remote_key WHERE id")
    suspend fun remoteKeyByQuery(id: Int?): RemoteKey

    @Query("DELETE FROM users")
    suspend fun deleteByQuery()
}

@Entity(tableName = "remote_key")
data class RemoteKey(
    @PrimaryKey val id: Int?,
    @ColumnInfo val prevKey: Int,
    @ColumnInfo val nextKey: Int

)