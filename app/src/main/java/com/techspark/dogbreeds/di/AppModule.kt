package com.techspark.dogbreeds.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.techspark.dogbreeds.R
import com.techspark.dogbreeds.data.remote.DogsAPI
import com.techspark.dogbreeds.repositories.DefaultDogBreedRepository
import com.techspark.dogbreeds.repositories.DogBreedRepository
import com.techspark.dogbreeds.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
@author Beshoy Fakhry

* Here we are setting all our dependencies that we will need within our application


* We should annotate this class with @Module annotation ,
 and then set the life cycle of this module to the application life cycle by setting
 InstallIn to ApplicationComponent

* What dependencies we need in our project
    1- The repo
    2- The Dog API.
    3- The Glide instance


* */
@Module
@InstallIn(ApplicationComponent::class)

object AppModule {


    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        api: DogsAPI
    ) = DefaultDogBreedRepository(api) as DogBreedRepository

    @Singleton
    @Provides
    fun provideDogApi() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(DogsAPI::class.java)


    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )
}