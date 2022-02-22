package com.example.practiseDemo.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.practiseDemo.apiData.Item
import com.example.practiseDemo.dao.RemoteKey
import com.example.practiseDemo.database.RoomDbApp
import com.example.practiseDemo.retrofit.RetrofitInterface
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class ExampleRemoteMediator(
    private val query: String,
    private val roomDbApp: RoomDbApp,
    private val api: RetrofitInterface
) : RemoteMediator<Int, Item>()
{

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Item>
    ): MediatorResult {

        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        return try {
            val response = api.getUserList(query, page)
            val isEndOfList = response.items.isEmpty()
            roomDbApp.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    roomDbApp.remoteKeyDao().deleteByQuery()
                    roomDbApp.userDao().clearAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.items.map {
                    RemoteKey(it.id!!, prevKey = prevKey, nextKey = nextKey)
                }
                roomDbApp.userDao().insertAll(response.items)
                roomDbApp.remoteKeyDao().insertOrReplace(response.items)

            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Item>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                prevKey

            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Item>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                roomDbApp.remoteKeyDao().remoteKeyByQuery(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Item>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { it -> roomDbApp.remoteKeyDao().remoteKeyByQuery(it.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Item>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { it -> roomDbApp.remoteKeyDao().remoteKeyByQuery(it.id) }
    }
}