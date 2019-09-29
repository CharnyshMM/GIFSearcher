package com.example.mikita.gifsearcher

import com.example.mikita.gifsearcher.Model.ResponseObjectModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call;
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET;
import retrofit2.http.Query
import javax.inject.Inject


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

}