package com.example.mikita.gifsearcher.Model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ImageObjectModel (
    val url: String,
    val width: String,
    val height: String,
    val size: String
)