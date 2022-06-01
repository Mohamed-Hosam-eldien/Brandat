package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("product")
    val productDetails: ProductDetails
)