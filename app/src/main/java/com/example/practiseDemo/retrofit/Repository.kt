package com.example.practiseDemo.retrofit
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.practiseDemo.apiData.Item
import com.example.practiseDemo.database.RoomDbApp
import com.example.practiseDemo.pagination.ExampleRemoteMediator
import kotlinx.coroutines.flow.Flow

class Repository(private val roomDb: RoomDbApp) {

    private val retrofitInterface: RetrofitInterface= RetrofitInterface.getInstance()

    @OptIn(ExperimentalPagingApi::class)
    fun fetchUsers(name: String): Flow<PagingData<Item>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false),
            remoteMediator = ExampleRemoteMediator(name, roomDb,retrofitInterface),
            pagingSourceFactory = { roomDb.userDao().pagingSource(name)}
        ).flow
    }

}










