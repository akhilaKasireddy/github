package com.example.practisedemo.apiData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class RetroData(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
) {

}

    @Entity(tableName = "users")
    data class Item(
        @ColumnInfo var avatar_url: String?=null,
        @PrimaryKey(autoGenerate = true) var id: Int?=0,
        @ColumnInfo var login: String?=null,
        @ColumnInfo var url: String?=null
    )




