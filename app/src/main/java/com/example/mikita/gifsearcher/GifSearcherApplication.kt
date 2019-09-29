package com.example.mikita.gifsearcher

import android.app.Application
import dagger.Module




@Module
class GifSearcherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DaggerNetworkComponent
            .builder()
            .networkModule(NetworkModule())
            .build()
    }

}