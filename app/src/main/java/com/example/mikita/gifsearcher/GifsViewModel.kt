package com.example.mikita.gifsearcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mikita.gifsearcher.Model.ResponseObjectModel

class GifsViewModel : ViewModel() {
    private val mutableGifsData: MutableLiveData<ResponseObjectModel> by lazy {
        GifsRepository.getTrendingGifs()
    }

    fun getGifs(): LiveData<ResponseObjectModel> {
        return mutableGifsData!!
    }
}