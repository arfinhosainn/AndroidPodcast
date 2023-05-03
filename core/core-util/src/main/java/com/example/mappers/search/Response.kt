package com.example.mappers.search

import com.example.mappers.Episode

data class Response(
    val items: List<Episode>,
    val next_url: String
)