package com.example.mikita.gifsearcher.Models

import com.squareup.moshi.Json



data class ImagesObjectModel (
    @Json(name="fixed_height") val fixedHeight: ImageObjectModel?,
    val downsized: ImageObjectModel?,
    val original: ImageObjectModel
)
