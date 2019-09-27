package com.example.mikita.gifsearcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mikita.gifsearcher.Model.GifObjectModel
import com.example.mikita.gifsearcher.Model.ImageObjectModel
import com.example.mikita.gifsearcher.Model.ResponseObjectModel
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call;
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val API_KEY:String = "TCjvJJXGE1qps7VXheY8Cy3ECHJfgXBt"


    var gifsAdapter: GifsAdapter? = null
    var viewModel:GifsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(GifsViewModel::class.java)
        gifsAdapter = GifsAdapter(this)

        viewModel!!.pagedGifsList.observe(this, Observer {
                list -> run {
                    Log.d("observer", "*submits a list*, len="+list.size)
                    gifsAdapter?.submitList(list)
                }
        })
        main__recycler_view.layoutManager = LinearLayoutManager(this)
        main__recycler_view.adapter = gifsAdapter

    }
}
