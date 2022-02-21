package com.example.practisedemo.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practisedemo.apiData.Item
import com.example.practisedemo.retrofit.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(private val repository: Repository, val app: Application) :
    AndroidViewModel(app) {
    var userInput = MutableStateFlow(" ")
    val itemsLiveData = MutableLiveData<PagingData<Item>>()


    init {
        viewModelScope.launch {
            userInput.collect {
                delay(2000)
                if (it == "") return@collect
                withContext(Dispatchers.IO) {
                    getAll(it)
                }
            }
        }
    }
    private suspend fun getAll(name: String) {
        try {
            if (isOnline()) {
                val list = repository.fetchUsers(name).cachedIn(viewModelScope)
                list.collectLatest {
                    itemsLiveData.postValue(it)
                }
            }
        } catch (e: Exception) {
            print(e.message)
        }
    }

    private fun isOnline(): Boolean {
        val connMgr = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

}







