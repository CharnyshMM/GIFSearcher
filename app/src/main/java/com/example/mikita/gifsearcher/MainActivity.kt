package com.example.mikita.gifsearcher

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var gifsAdapter: GifsAdapter? = null
    var viewModel: AppViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fresco.initialize(applicationContext)

        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        handleIntent(intent)

        if (hasInternetConnection) {
            setLoading()
        } else {
            Toast.makeText(this, "No internet", Toast.LENGTH_LONG).show()
        }

        main__swipe_refresh_layout.setOnRefreshListener {
            if (hasInternetConnection) {
                viewModel?.dataSource?.value?.invalidate()
                setLoading()
            } else {
                Toast.makeText(this, getString(R.string.check_your_connection), Toast.LENGTH_LONG).show()
                main__swipe_refresh_layout.isRefreshing = false
            }
        }

        main__search_edit_text.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    this@MainActivity.triggerSearch(v?.text.toString(), null)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    v?.clearFocus()
                    v?.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    return true
                }
                return false
            }

        })

    }

    private fun setLoading() {
        gifsAdapter = GifsAdapter(this, this.resources.configuration.orientation)

        viewModel!!.networkState.observe(this, Observer {
            if (it == NetworkState.LOADING) {
                Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_LONG).show()
            } else if (it == NetworkState.ERROR) {
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
                stopSwipeLayourRefreshing()
            } else if (it == NetworkState.SERVER_ERROR) {
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
                stopSwipeLayourRefreshing()
            } else {
                if (main__swipe_refresh_layout.isRefreshing) {
                    main__swipe_refresh_layout.isRefreshing = false
                }
            }
        })
        viewModel!!.pagedGifsList.observe(this, Observer { list ->
            run {
                gifsAdapter?.submitList(list)
            }
        })

        if (this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            main__recycler_view.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        } else {
            main__recycler_view.layoutManager = LinearLayoutManager(this)
        }

        main__recycler_view.adapter = gifsAdapter

    }

    private val hasInternetConnection: Boolean
        get() {
            val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnected == true
        }

    private fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            viewModel!!.queryString.value = (query)
        } else {
            viewModel!!.queryString.value = null
        }
    }

    private fun clearSearch() {
        viewModel!!.queryString.postValue(null)
        main__search_edit_text.setText("")
        main__search_edit_text.clearFocus()
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    private fun stopSwipeLayourRefreshing() {
        if (main__swipe_refresh_layout.isRefreshing) {
            main__swipe_refresh_layout.isRefreshing = false
        }
    }

    override fun onNewIntent(intent: Intent?) {
        handleIntent(intent)
        super.onNewIntent(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (viewModel!!.queryString.value != null && viewModel!!.queryString.value != "") {
            clearSearch()
        } else {
            super.onBackPressed()
        }
    }
}
