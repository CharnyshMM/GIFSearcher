package com.example.mikita.gifsearcher.Model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ResponseObjectModel (
    val data: List<GifObjectModel>,
    val pagination: PaginationObjectModel,
    val meta: MetaObjectModel
)