package com.example.mikita.gifsearcher

import com.example.mikita.gifsearcher.Model.ResponseObjectModel
import retrofit2.Call;
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query

interface GiphyAPIService {
    @GET("gifs/trending")
    fun getTrendingGifs(@Query("api_key") apiKey: String,
                        @Query("limit") limit: Int = 10,
                        @Query("offset") offset: Int = 0): Call<ResponseObjectModel>

    @GET("gifs/search")
    fun getGifsByQuery(@Query("api_key") apiKey: String,
                        @Query("q") query: String,
                        @Query("limit") limit: Int = 10,
                        @Query("offset") offset: Int = 0): Call<ResponseObjectModel>


    companion object Factory {
        fun create():GiphyAPIService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://api.giphy.com/v1/")
                .build()

            return retrofit.create(GiphyAPIService::class.java)
        }
    }
}