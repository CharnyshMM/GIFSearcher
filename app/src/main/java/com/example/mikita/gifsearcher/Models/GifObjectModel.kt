package com.example.mikita.gifsearcher.Models


data class GifObjectModel (
    val type: String,
    val id: String,
    val rating: String,
    val source: String,
    val title: String,
    val slug: String,
    val url: String,
    val username: String,
    val images: ImagesObjectModel
)