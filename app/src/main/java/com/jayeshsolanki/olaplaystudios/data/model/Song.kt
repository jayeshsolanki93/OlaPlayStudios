package com.jayeshsolanki.olaplaystudios.data.model

import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("song") val name: String,
    @SerializedName("url") val url: String,
    @SerializedName("artists") val artists: String,
    @SerializedName("cover_image") val coverImageUrl: String
)
