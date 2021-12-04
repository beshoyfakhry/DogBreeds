package com.techspark.dogbreeds.data.remote

import com.techspark.dogbreeds.BuildConfig
import com.techspark.dogbreeds.data.remote.items.DogBreedsItems
import com.techspark.dogbreeds.data.remote.items.ImageLinkItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DogsAPI {

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("breeds")
    suspend fun listDogBreeds(): Response<MutableList<DogBreedsItems>>

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("images/search")
    suspend fun listSelectedDogBreedImages(
        @Query("limit") limit: String,
        @Query("page") page: Int,
        @Query("breed_id") breedId: String,
        @Query("order") order: String = "ASC"
    ): Response<MutableList<ImageLinkItem>>

}