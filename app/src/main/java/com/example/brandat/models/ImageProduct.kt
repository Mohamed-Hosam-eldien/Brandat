package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class ImageProduct(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String = "",
    @SerializedName("alt")
    val alt: Any = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("position")
    val position: Int = 0,
    @SerializedName("product_id")
    val productId: Long = 0,
    @SerializedName("src")
    val src: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("variant_ids")
    val variantIds: List<Any> = listOf(),
    @SerializedName("width")
    val width: Int = 0
)