package com.example.mikita.gifsearcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mikita.gifsearcher.Model.GifObjectModel
import com.example.mikita.gifsearcher.Model.ResponseObjectModel

class GifsViewModel : ViewModel() {
    val mutableGifsData: MutableLiveData<ResponseObjectModel> by lazy {
        GifsRepository.getTrendingGifs()
    }

    val pagedGifsList: LiveData<PagedList<GifObjectModel>> = LivePagedListBuilder<Int, GifObjectModel>(
        GifsDataSource.GifsDataSourceFactory,
        PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
    ).build()
}