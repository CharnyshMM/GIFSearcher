package com.example.mikita.gifsearcher.Networking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.example.mikita.gifsearcher.BuildConfig
import com.example.mikita.gifsearcher.Models.GifObjectModel
import com.example.mikita.gifsearcher.Models.ResponseObjectModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GifsDataSource(val queryString: String?) : PositionalDataSource<GifObjectModel>() {
    var giphyAPIService: GiphyAPIService =
        GiphyAPIService.create()

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<GifObjectModel>) {
        var call: Call<ResponseObjectModel>? = null
        networkState.postValue(NetworkState.LOADING)
        if (queryString == null || queryString == "") {
            call = giphyAPIService
                .getTrendingGifs(
                    BuildConfig.GIPHY_API_KEY,
                    limit = params.requestedLoadSize,
                    offset = params.requestedStartPosition
                )
        } else {
            call = giphyAPIService
                .getGifsByQuery(
                    BuildConfig.GIPHY_API_KEY,
                    query = queryString,
                    limit = params.requestedLoadSize,
                    offset = params.requestedStartPosition
                )
        }
        call.enqueue(object : Callback<ResponseObjectModel> {
            override fun onFailure(call: Call<ResponseObjectModel>, t: Throwable) {
                Log.d("GifsRepo", "on retrofit failure")
                networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<ResponseObjectModel>, response: Response<ResponseObjectModel>) {
                if (response.isSuccessful) {
                    val body = response.body()!!
                    Log.d("onResponse", "loaded")
                    Log.d("Total count", "=" + body.pagination.total_count)
                    callback.onResult(body.data, params.requestedStartPosition, body.pagination.total_count)
                    networkState.postValue(NetworkState.OK)
                } else {
                    networkState.postValue(NetworkState.SERVER_ERROR)
                }
            }
        })
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<GifObjectModel>) {
        var call: Call<ResponseObjectModel>? = null
        networkState.postValue(NetworkState.LOADING)
        if (queryString == null || queryString == "") {
            call = giphyAPIService
                .getTrendingGifs(
                    BuildConfig.GIPHY_API_KEY,
                    limit = params.loadSize,
                    offset = params.startPosition
                )
        } else {
            call = giphyAPIService
                .getGifsByQuery(
                    BuildConfig.GIPHY_API_KEY,
                    query = queryString,
                    limit = params.loadSize,
                    offset = params.startPosition
                )
        }

        call.enqueue(object : Callback<ResponseObjectModel> {
            override fun onFailure(call: Call<ResponseObjectModel>, t: Throwable) {
                networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: Call<ResponseObjectModel>, response: Response<ResponseObjectModel>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    callback.onResult(body!!.data)// body.pagination.count+ body.pagination.offset, body.pagination.total_count)
                    networkState.postValue(NetworkState.OK)
                } else {
                    networkState.postValue(NetworkState.SERVER_ERROR)
                }
            }
        })
    }


    class GifsDataSourceFactory(val mutableQueryString: MutableLiveData<String>) :
        DataSource.Factory<Int, GifObjectModel>() {
        val mutableDataSource: MutableLiveData<GifsDataSource> = MutableLiveData<GifsDataSource>()

        override fun create(): DataSource<Int, GifObjectModel> {
            val dataSource = GifsDataSource(mutableQueryString.value)
            mutableDataSource.postValue(dataSource)
            return dataSource
        }
    }

}