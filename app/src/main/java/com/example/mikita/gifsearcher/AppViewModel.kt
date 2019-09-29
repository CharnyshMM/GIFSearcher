package com.example.mikita.gifsearcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mikita.gifsearcher.Model.GifObjectModel

class AppViewModel : ViewModel() {

    val queryString: MutableLiveData<String> = MutableLiveData<String>(null)

    val networkState: LiveData<NetworkState>

    val dataSource: MutableLiveData<GifsDataSource>

    val pagedGifsList: LiveData<PagedList<GifObjectModel>>

    init {
        val factory = GifsDataSource.GifsDataSourceFactory(queryString)

        dataSource = factory.mutableDataSource

        networkState = Transformations.switchMap(factory.mutableDataSource) {
            it.networkState
        }

        pagedGifsList= Transformations.switchMap(queryString) {
            LivePagedListBuilder<Int, GifObjectModel>(
                factory,
                PagedList.Config.Builder()
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .setEnablePlaceholders(false)
                    .build()
            ).build()
        }
    }
}