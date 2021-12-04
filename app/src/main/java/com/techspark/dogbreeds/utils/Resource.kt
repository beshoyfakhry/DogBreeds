package com.techspark.dogbreeds.utils


/*

@author Beshoy Fakhry
* This is a class we can call it a wrapping class , we get the response from the api,
* and then we set it to that class with the corresponding status [Success , Error]
* and we also use this class for the [ Loading ] status
* */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}