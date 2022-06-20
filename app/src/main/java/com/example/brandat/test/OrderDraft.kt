package com.example.brandat.test

import com.google.gson.annotations.SerializedName


data class OrderDraft(
    val order: DraftFav
)

data class DraftFav(

    @SerializedName("id")
    val id: Long? = 0,

    @SerializedName("email")
    val email: String = "",

    @SerializedName("line_items")
    val line_items: List<LineItem>? = listOf()
)


