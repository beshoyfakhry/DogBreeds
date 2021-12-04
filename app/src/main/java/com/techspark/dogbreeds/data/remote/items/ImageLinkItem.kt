package com.techspark.dogbreeds.data.remote.items

import com.google.gson.annotations.SerializedName


data class ImageLinkItem(
    @SerializedName("url")
    val ImageUrl: String?
)