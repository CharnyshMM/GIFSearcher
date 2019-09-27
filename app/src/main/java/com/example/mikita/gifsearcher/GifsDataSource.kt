package com.example.mikita.gifsearcher

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.example.mikita.gifsearcher.Model.GifObjectModel
import com.example.mikita.gifsearcher.Model.ResponseObjectModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GifsDataSource : PositionalDataSource<GifObjectModel>() {
    val giphyAPIService = GiphyAPIService.create()

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<GifObjectModel>) {
        Log.d("GifsDataSource.loadInit", "reqLoadSIze="+params.requestedLoadSize+" ReqStartPos="+params.requestedStartPosition+" pageSize="+ params.pageSize)
        giphyAPIService
            .getTrendingGifs(GifsRepository.API_KEY, limit=params.pageSize, offset=params.requestedStartPosition)
            .enqueue(object: Callback<ResponseObjectModel> {
                override fun onFailure(call: Call<ResponseObjectModel>, t: Throwable) {
                    Log.d("GifsRepo", "on retrofit failure")
                }

                override fun onResponse(call: Call<ResponseObjectModel>, response: Response<ResponseObjectModel>) {
                    if (response.isSuccessful) {
                        val body = response.body()!!
                        Log.d("onResponse", "loaded")
                        Log.d("Total count", "="+body.pagination.total_count)
                        callback.onResult(body.data, body.pagination.offset + body.pagination.count, body.pagination.total_count)
                    }
                }
            })
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<GifObjectModel>) {
        Log.d("loadRange", "params.startPosition="+params.startPosition)
        giphyAPIService
            .getTrendingGifs(GifsRepository.API_KEY, limit=params.loadSize, offset=params.startPosition)
            .enqueue(object: Callback<ResponseObjectModel> {
                override fun onFailure(call: Call<ResponseObjectModel>, t: Throwable) {
                    Log.d("GifsRepo", "on retrofit failure")
                }

                override fun onResponse(call: Call<ResponseObjectModel>, response: Response<ResponseObjectModel>) {
                    if (response.isSuccessful) {
                        val result: ResponseObjectModel = response.body()!!
                        val body = response.body()
                        callback.onResult(body!!.data)// body.pagination.count+ body.pagination.offset, body.pagination.total_count)
                    }
                }
            })

    }


    companion object GifsDataSourceFactory : DataSource.Factory<Int, GifObjectModel>() {
        override fun create(): DataSource<Int, GifObjectModel> {
            return GifsDataSource()
        }
    }

}