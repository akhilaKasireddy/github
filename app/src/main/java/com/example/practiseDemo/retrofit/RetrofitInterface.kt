package com.example.practiseDemo.retrofit


import com.example.practiseDemo.apiData.RetroData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("users")
    suspend fun getUserList(@Query("q") name: String, @Query("page") page: Int): RetroData

    companion object {
        private const val baseUrl = "https://api.github.com/search/"
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

