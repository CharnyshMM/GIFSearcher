package com.example.mikita.gifsearcher

import android.app.SearchManager
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var gifsAdapter: GifsAdapter? = null
    var viewModel:AppViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fresco.initialize(applicationContext)

        if (hasInternetConnection) {
            setUpLoading()
        } else {
            Toast.makeText(this, "No internet", Toast.LENGTH_LONG).show()
        }


    }

    private fun setUpLoading() {
        viewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        handleIntent(intent)

        gifsAdapter = GifsAdapter(this)

        viewModel!!.networkState.observe(this, Observer {
            if (it  == NetworkState.LOADING) {
                Toast.makeText(this, "loading", Toast.LENGTH_LONG).show()
            } else if (it == NetworkState.ERROR) {
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
                if (main__swipe_refresh_layout.isRefreshing) {
                    main__swipe_refresh_layout.isRefreshing = false
                }
            } else {
                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
                if (main__swipe_refresh_layout.isRefreshing) {
                    main__swipe_refresh_layout.isRefreshing = false
                }
            }
        })
        viewModel!!.pagedGifsList.observe(this, Observer {
                list -> run {
            Log.d("observer", "*submits a list*, len=")
            gifsAdapter?.submitList(list)
        }
        })


        main__recycler_view.layoutManager = LinearLayoutManager(this)
        main__recycler_view.adapter = gifsAdapter

        main__swipe_refresh_layout.setOnRefreshListener {
            viewModel?.dataSource?.value?.invalidate()
        }

    }

    private var hasInternetConnection:Boolean = false
        get() {
            val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return  activeNetwork?.isConnected == true
        }

    private fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            viewModel!!.queryString.value = (query)
        } else {
            viewModel!!.queryString.value = null
        }
    }

    override fun onNewIntent(intent: Intent?) {
        handleIntent(intent)
        super.onNewIntent(intent)
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
