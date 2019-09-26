package com.example.mikita.gifsearcher.Model

import com.squareup.moshi.Json


data class PaginationObjectModel (
    @Json(name="total_count")val totalCount: Int,
    val count: Int,
    val offset: Int
)