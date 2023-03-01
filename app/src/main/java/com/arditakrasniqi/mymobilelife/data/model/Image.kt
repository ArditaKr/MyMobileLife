package com.arditakrasniqi.mymobilelife.data.model

import java.net.URL

data class Image(
    val id: String,
    val author: String,
    val width: Long,
    val height: Long,
    val url: URL,
    val download_url: URL
)
