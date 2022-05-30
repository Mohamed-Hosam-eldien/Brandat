package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class Brands(
    @SerializedName("smart_collections")
    val smartCollections: List<SmartCollection>
)