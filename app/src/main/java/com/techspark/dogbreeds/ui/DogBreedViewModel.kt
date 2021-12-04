package com.techspark.dogbreeds.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techspark.dogbreeds.data.remote.items.DogBreedsItems
import com.techspark.dogbreeds.data.remote.items.ImageLinkItem
import com.techspark.dogbreeds.repositories.DogBreedRepository
import com.techspark.dogbreeds.utils.Constants
import com.techspark.dogbreeds.utils.Resource
import kotlinx.coroutines.launch

class DogBreedViewModel @ViewModelInject constructor(
    private val repository: DogBreedRepository
) : ViewModel() {

    private val _dogBreeds = MutableLiveData<Resource<MutableList<DogBreedsItems>>>()
    val dogBreeds: LiveData<Resource<MutableList<DogBreedsItems>>> = _dogBreeds

    private val _breedImages = MutableLiveData<Resource<MutableList<ImageLinkItem>>>()
    val breedImages: LiveData<Resource<MutableList<ImageLinkItem>>> = _breedImages

    var requestLimit = 50
    var requestPage = 0
    var selectedBreadId = ""
    var totalNumberOfItemsReceived = 0

    private fun listDogBreeds() {
        _dogBreeds.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.observeDogBreeds()
            _dogBreeds.value = response
        }
    }

    fun listImagesForSelectedDogBreed() {
        _dogBreeds.value = Resource.loading(null)
        viewModelScope.launch {

            if (totalNumberOfItemsReceived + requestLimit > Constants.MAX_ITEMS_NUMBER) {

                requestLimit = Constants.MAX_ITEMS_NUMBER - totalNumberOfItemsReceived
            }

            val response =
                repository.observeSelectedDogBreedImages(
                    requestLimit.toString(),
                    requestPage,
                    selectedBreadId
                )
            if (response.data!!.size > 0) {
                _breedImages.value = response
                totalNumberOfItemsReceived += _breedImages.value!!.data!!.size
                requestPage++
            }

        }
    }

    init {
        listDogBreeds()
    }
}












