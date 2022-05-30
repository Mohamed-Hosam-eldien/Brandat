package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class SmartCollection(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("body_html")
    val bodyHtml: String,
    @SerializedName("disjunctive")
    val disjunctive: Boolean,
    @SerializedName("handle")
    val handle: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("image")
    val image: ImageBrand,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("published_scope")
    val publishedScope: String,
    @SerializedName("rules")
    val rules: List<Rule>,
    @SerializedName("sort_order")
    val sortOrder: String,
    @SerializedName("template_suffix")
    val templateSuffix: Any,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
)