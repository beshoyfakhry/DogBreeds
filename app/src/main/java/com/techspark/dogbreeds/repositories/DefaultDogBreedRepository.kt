package com.techspark.dogbreeds.repositories

import com.techspark.dogbreeds.data.remote.DogsAPI
import com.techspark.dogbreeds.data.remote.items.DogBreedsItems
import com.techspark.dogbreeds.data.remote.items.ImageLinkItem
import com.techspark.dogbreeds.utils.Resource
import javax.inject.Inject

class DefaultDogBreedRepository @Inject constructor(
    private val dogsAPI: DogsAPI
) : DogBreedRepository {

    override suspend fun observeDogBreeds(): Resource<MutableList<DogBreedsItems>> {
        return try {
            val response = dogsAPI.listDogBreeds()
            if (response.isSuccessful) {
                response.body()?.let {

                    val defaultValue = DogBreedsItems(0, "Please select a dog breed")
                    it.add(0, defaultValue)
                    val nullAndEmptyDataList = mutableListOf<DogBreedsItems>()

                    for (item in it) {
                        if (item.breedTitle.isNullOrEmpty()) {
                            nullAndEmptyDataList.add(item)
                        }
                    }

                    it.removeAll(nullAndEmptyDataList)
                    nullAndEmptyDataList.clear()
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Exception Caught ${e.localizedMessage}", null)
        }
    }

    override suspend fun observeSelectedDogBreedImages(
        limit: String,
        page: Int,
        breedId: String
    ): Resource<MutableList<ImageLinkItem>> {
        return try {
            val response = dogsAPI.listSelectedDogBreedImages(limit, page, breedId)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Exception Caught ${e.localizedMessage}", null)
        }
    }
}