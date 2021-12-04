package com.techspark.dogbreeds.data.remote.items

import com.google.gson.annotations.SerializedName


data class DogBreedsItems(
    val id: Int,
    @SerializedName("bred_for")
    val breedTitle: String?
) {

    override fun toString(): String = breedTitle!!
}