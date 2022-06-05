package com.example.brandat.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductDetails(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("body_html")
    val bodyHtml: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("handle")
    val handle: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("image")
    val imageProduct: ImageProduct,
    @SerializedName("images")
    val imageProducts: List<ImageProduct>,
    @SerializedName("options")
    val options: List<Option>,
    @SerializedName("product_type")
    val productType: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("published_scope")
    val publishedScope: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("template_suffix")
    val templateSuffix: Any,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("variants")
    val variants: List<Variant>,
    @SerializedName("vendor")
    val vendor: String
):Serializable