package com.example.mikita.gifsearcher.Models

import com.squareup.moshi.Json


data class PaginationObjectModel (
    val count: Int,
    @Json(name="total_count")
    val total_count: Int,
    val offset: Int
)