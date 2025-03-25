package com.example.task.data.api.retrofit

import com.example.task.data.api.model.Cat
import retrofit2.http.GET

interface WebService {

    @GET("images/search")
    suspend fun getCats(): List<Cat>
}
