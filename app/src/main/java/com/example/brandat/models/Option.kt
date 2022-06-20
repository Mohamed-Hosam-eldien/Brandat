package com.example.brandat.models


import com.google.gson.annotations.SerializedName
////////////////
data class Option(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("position")
    val position: Int = 0,
    @SerializedName("product_id")
    val productId: Long = 0,
    @SerializedName("values")
    val values: List<String> = listOf()
)