package com.example.mikita.gifsearcher.Model


data class ResponseObjectModel (
    val data: List<GifObjectModel>,
    val pagination: PaginationObjectModel,
    val meta: MetaObjectModel
)