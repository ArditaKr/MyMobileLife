package com.arditakrasniqi.mymobilelife.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.net.URL

@Parcelize
data class Image(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: URL,
    val download_url: URL
) : Parcelable


