package com.example.brandat.models


import com.google.gson.annotations.SerializedName
///////////////
data class Rule(
    @SerializedName("column")
    val column: String,
    @SerializedName("condition")
    val condition: String,
    @SerializedName("relation")
    val relation: String
)