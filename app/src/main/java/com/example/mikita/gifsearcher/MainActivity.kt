package com.example.mikita.gifsearcher

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mikita.gifsearcher.Model.GifObjectModel
import com.example.mikita.gifsearcher.Model.ImageObjectModel
import com.example.mikita.gifsearcher.Model.ResponseObjectModel
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call;
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var gifsAdapter: GifsAdapter? = null
    var viewModel:GifsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fresco.initialize(applicationContext)


        viewModel = ViewModelProviders.of(this).get(GifsViewModel::class.java)

        handleIntent(intent)
        gifsAdapter = GifsAdapter(this)

        viewModel!!.pagedGifsList.observe(this, Observer {
                list -> run {
                    Log.d("observer", "*submits a list*, len=")

                    gifsAdapter?.submitList(list)
                }
        })
        main__recycler_view.layoutManager = LinearLayoutManager(this)
        main__recycler_view.adapter = gifsAdapter

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchManager.setOnCancelListener {
            Toast.makeText(applicationContext, "on cancle", Toast.LENGTH_LONG).show()
        }
        searchManager.setOnDismissListener {
            Toast.makeText(applicationContext, "on dismiss", Toast.LENGTH_LONG).show()
        }
    }

    fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            viewModel!!.queryString.value = (query)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        handleIntent(intent)
        super.onNewIntent(intent)
    }

    fun isConnectionAvailable():Boolean {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        (menu?.findItem(R.id.search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }
}
