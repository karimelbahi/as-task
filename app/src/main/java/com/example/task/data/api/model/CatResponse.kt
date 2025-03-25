package com.example.task.data.api.model

import com.google.gson.annotations.SerializedName

data class Cat(
    @Transient
    val height: Int? = null,
    @Transient
    val id: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @Transient
    val width: Int? = null
)