package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class ImageBrand(
    @SerializedName("alt")
    val alt: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("src")
    val src: String,
    @SerializedName("width")
    val width: Int
)