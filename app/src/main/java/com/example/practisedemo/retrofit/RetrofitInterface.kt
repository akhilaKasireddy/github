package com.example.practisedemo.retrofit


import com.example.practisedemo.apiData.RetroData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("search/users")
    suspend fun getUserList(@Query("q") name: String, loadType: Int): RetroData






    companion object {
        private const val baseUrl = "https://api.github.com/"
        private var retrofitService: RetrofitInterface? = null
        fun getInstance(): RetrofitInterface {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
                retrofitService = retrofit.create(RetrofitInterface::class.java)
            }
            return retrofitService!!
        }
    }
}

