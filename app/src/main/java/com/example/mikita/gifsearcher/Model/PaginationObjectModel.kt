package com.example.mikita.gifsearcher.Model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PaginationObjectModel (
    @Json(name="total_count")val totalCount: Int,
    val count: Int,
    val offset: Int
)