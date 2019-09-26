package com.example.mikita.gifsearcher.Model


data class GifObjectModel (
    val type: String,
    val id: String,
    val rating: String,
    val source: String,
    val title: String,
    val images: ImagesObjectModel
)