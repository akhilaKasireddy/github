package com.example.practiseDemo.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practiseDemo.retrofit.Repository


class ViewModelFactory(
    private val mRepository: Repository,
    private val application: Application,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            MyViewModel(this.mRepository, application) as T

        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}





