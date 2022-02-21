package com.example.practisedemo.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.practisedemo.apiData.Item
import com.example.practisedemo.dao.RemoteKey
import com.example.practisedemo.database.RoomDbApp
import com.example.practisedemo.retrofit.RetrofitInterface
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ExampleRemoteMediator(
    private val query: String,
    private val roomDbApp: RoomDbApp,
    private val api: RetrofitInterface
) : RemoteMediator<Int, Item>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Item>
    ): MediatorResult {
        val page=when (val pageKey = getPagedData(state, loadType)){
            is MediatorResult.Success ->{
                return pageKey
            }else -> {
                pageKey as Int
            }
        }
        return try {
            val response=api.getUserList(query,page)
            val isListEmpty=response.items.isEmpty()
            roomDbApp.withTransaction {
                if (loadType== LoadType.REFRESH){
                    roomDbApp.remoteKeyDao().deleteByQuery()
                   roomDbApp.userDao().deleteByQuery()
                }
                val prevKey=if (page==1) null else page -1
                val nextKey=if (isListEmpty) null else page +1

                val keys=response.items.map {
                    if (prevKey != null) {
                        if (nextKey != null) {
                            RemoteKey(it.id, nextKey,prevKey)
                        }
                    }
                }
                roomDbApp.userDao().insertAll(response.items)
                roomDbApp.remoteKeyDao().insertOrReplace(response.items)
            }
            return MediatorResult.Success(endOfPaginationReached =isListEmpty )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getPagedData(state: PagingState<Int, Item>, loadType: LoadType): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getCurrentPosition(state)
                return remoteKeys!!.nextKey.minus(1)
            }
            LoadType.PREPEND -> {
                 val remoteKeys = getFirstPosition(state)
                val prevKey= remoteKeys?.prevKey?:MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND-> {
                val remoteKeys = getLastRemoteKey(state, loadType)
                return remoteKeys?.nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
        }
    }
    private suspend fun getLastRemoteKey(state: PagingState<Int, Item>, loadType: LoadType): RemoteKey? {
        return state.pages.lastOrNull {
            it.data.isEmpty()
        }
            ?.data?.lastOrNull()
            ?.let { item ->
                roomDbApp.remoteKeyDao(). remoteKeyByQuery(item.id)
            }

    }

    private suspend fun getFirstPosition(state: PagingState<Int, Item>): RemoteKey? {
        return state.pages.firstOrNull {
            it.data.isEmpty()
        }
            ?.data?.firstOrNull()
            ?.let { item ->
                roomDbApp.remoteKeyDao().remoteKeyByQuery(item.id)
            }
    }

    private suspend fun getCurrentPosition(state: PagingState<Int, Item>): RemoteKey? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id.let { id ->
                roomDbApp.remoteKeyDao().remoteKeyByQuery(id)
            }

        }
    }
}
