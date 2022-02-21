package com.example.practisedemo.dao
import androidx.paging.PagingSource
import androidx.room.*
import com.example.practisedemo.apiData.Item


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Item>)

    @Query("SELECT * FROM users")
    fun getAll(): PagingSource<Int, Item>

    @Query("DELETE  FROM users")
    fun deleteByQuery()
}

@Entity(tableName = "UserTable")
data class UserDbData(
    @ColumnInfo(name = "username")
    var userName: String? = "",
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,
    @ColumnInfo(name = "user repo")
    var userRepo: String? = "",
    @ColumnInfo(name = "user image")
    var userImage: String? = ""
)

