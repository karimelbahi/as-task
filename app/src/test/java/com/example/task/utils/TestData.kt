package com.example.task.utils

import com.example.task.data.api.model.Cat
import com.example.task.domain.model.CatUIModel

val fakeCat = Cat(url = "https://example.com/same_cat.jpg", id = "1", height = 100, width = 150)
val fakeCats = List(10) { fakeCat }


fun List<Cat>.toCatUIModel() = CatUIModel(this)

const val ERROR_MESSAGE = "Error message"
const val CUSTOM_ERROR_MESSAGE = "Custom error message"
const val MSG_UNKNOWN_ERROR = "Unknown Error Occurred."
const val MSG_NOT_FOUND_ERROR = "Sorry, the requested item not available."
const val MSG_SERVICE_UNAVAILABLE = "Sorry, the requested item not available."
const val MSG_NETWORK_ERROR = "Network error, Please check your internet connection."
const val NO_INTERNET_CONNECTION = "No Internet Connection"
const val BAD_REQUEST ="Bad Request"

