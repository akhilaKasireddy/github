package com.example.practisedemo.retrofit
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.practisedemo.apiData.Item
import com.example.practisedemo.database.RoomDbApp
import com.example.practisedemo.pagination.ExampleRemoteMediator
import kotlinx.coroutines.flow.Flow

class Repository(private val retrofitInterface: RetrofitInterface,private val roomDb: RoomDbApp) {


    @OptIn(ExperimentalPagingApi::class)
    fun fetchUsers(name: String): Flow<PagingData<Item>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = ExampleRemoteMediator(name, roomDb,retrofitInterface),
            pagingSourceFactory = { roomDb.userDao().getAll() }
        ).flow
    }

}










