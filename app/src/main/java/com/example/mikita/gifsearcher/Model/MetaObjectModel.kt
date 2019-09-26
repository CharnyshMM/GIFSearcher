package com.example.mikita.gifsearcher.Model

import com.squareup.moshi.Json



data class MetaObjectModel (
    val status: Int,
    val msg: String,
    @Json(name="response_id") val responseId: String
)