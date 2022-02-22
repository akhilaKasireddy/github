package com.example.practiseDemo.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_Table")
    data class UserTable(@PrimaryKey val id: String,
                         val name: String)