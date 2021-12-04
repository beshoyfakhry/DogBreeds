package com.techspark.dogbreeds.repositories

import com.techspark.dogbreeds.data.remote.items.DogBreedsItems
import com.techspark.dogbreeds.data.remote.items.ImageLinkItem
import com.techspark.dogbreeds.utils.Resource


/*
@author Beshoy Fakhry
* We have done this interface in order to use it in both repositories, the real repo and the
 fake repo when we create the test cases.
* */
interface DogBreedRepository {

    suspend fun observeDogBreeds(): Resource<MutableList<DogBreedsItems>>

    suspend fun observeSelectedDogBreedImages(
        limit: String,
        page: Int,
        breedId: String
    ): Resource<MutableList<ImageLinkItem>>


}