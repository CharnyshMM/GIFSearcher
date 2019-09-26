package com.example.mikita.gifsearcher

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.mikita.gifsearcher.Model.ResponseObjectModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GifsRepository {
    val API_KEY:String = "TCjvJJXGE1qps7VXheY8Cy3ECHJfgXBt"

    var giphyAPIService = GiphyAPIService.create()

    fun getTrendingGifs(limit:Int = 10, offset:Int = 0): MutableLiveData<ResponseObjectModel> {
        val gifsData: MutableLiveData<ResponseObjectModel> = MutableLiveData();
        giphyAPIService
            .getTrendingGifs(API_KEY, limit, offset)
            .enqueue(object: Callback<ResponseObjectModel> {
                override fun onFailure(call: Call<ResponseObjectModel>, t: Throwable) {
                    Log.d("GifsRepo", "on retrofit failure")
                    gifsData.value = null
                }

                override fun onResponse(call: Call<ResponseObjectModel>, response: Response<ResponseObjectModel>) {
                    if (response.isSuccessful) {
                        val result: ResponseObjectModel = response.body()!!
                        gifsData.value = result
                    }
                }
            })
        return gifsData
    }


    fun findGifs(query:String, limit:Int = 10, offset:Int = 0): MutableLiveData<ResponseObjectModel> {
        val gifsData: MutableLiveData<ResponseObjectModel> = MutableLiveData();
        giphyAPIService
            .getGifsByQuery(API_KEY, query, limit, offset)
            .enqueue(object: Callback<ResponseObjectModel> {
                override fun onFailure(call: Call<ResponseObjectModel>, t: Throwable) {
                    Log.d("GifsRepo", "on retrofit failure")
                    gifsData.value = null
                }

                override fun onResponse(call: Call<ResponseObjectModel>, response: Response<ResponseObjectModel>) {
                    if (response.isSuccessful) {
                        val result: ResponseObjectModel = response.body()!!
                        gifsData.value = result
                    }
                }
            })
        return gifsData
    }
}