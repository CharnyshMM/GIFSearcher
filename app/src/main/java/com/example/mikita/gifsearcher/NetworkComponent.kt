package com.example.mikita.gifsearcher

import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun service():GiphyAPIService
    fun retrofit():Retrofit
}