package com.example.mikita.gifsearcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mikita.gifsearcher.Model.GifObjectModel

class GifsViewModel : ViewModel() {

    val queryString: MutableLiveData<String> = MutableLiveData<String>(null)


    var pagedGifsList: LiveData<PagedList<GifObjectModel>> = Transformations.switchMap(queryString) {
                     LivePagedListBuilder<Int, GifObjectModel>(
                        GifsDataSource.GifsDataSourceFactory(queryString.value),
                        PagedList.Config.Builder()
                            .setInitialLoadSizeHint(10)
                            .setPageSize(10)
                            .setEnablePlaceholders(false)
                            .build()
                    ).build()
            }




}