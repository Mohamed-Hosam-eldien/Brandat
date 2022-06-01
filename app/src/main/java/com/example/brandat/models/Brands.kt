package com.example.brandat.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Brands(
    @SerializedName("smart_collections")
    val brands: List<Brand>

):Serializable