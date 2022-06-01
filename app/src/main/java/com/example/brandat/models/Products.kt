package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("products")
    val products: List<ProductDetails>
)