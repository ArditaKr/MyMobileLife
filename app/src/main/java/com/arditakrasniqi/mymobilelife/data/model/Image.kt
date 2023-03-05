package com.arditakrasniqi.mymobilelife.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.net.URL

/** Image is a model, based on it we will get data from API
 * @Parcelize is annotation that helps us to use when we need to send object of type Image from one screen to another.
 * Navigation Component don't support all types of models, so we need to make it parcelable
 * */
@Parcelize
data class Image(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: URL,
    val download_url: URL
) : Parcelable


