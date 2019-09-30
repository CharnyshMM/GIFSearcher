package com.example.mikita.gifsearcher.Models


data class ResponseObjectModel (
    val data: List<GifObjectModel>,
    val pagination: PaginationObjectModel,
    val meta: MetaObjectModel
)