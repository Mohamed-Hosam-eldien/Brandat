package com.example.brandat.models
import com.google.gson.annotations.SerializedName
import java.io.Serializable
data class Brand(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String = "",
    @SerializedName("body_html")
    val bodyHtml: String = "",
    @SerializedName("disjunctive")
    val disjunctive: Boolean = false,
    @SerializedName("handle")
    val handle: String = "",
    @SerializedName("id")
    val id: Long = 0L,//=====================
    @SerializedName("image")
    val image: ImageBrand? = null,//===================
    @SerializedName("published_at")
    val publishedAt: String = "",
    @SerializedName("published_scope")
    val publishedScope: String = "",
    @SerializedName("rules")
    val rules: List<Rule>? = null,
    @SerializedName("sort_order")
    val sortOrder: String = "",
    @SerializedName("template_suffix")
    val templateSuffix: Any = "",
    @SerializedName("title")
    val title: String = "",//========================
    @SerializedName("updated_at")
    val updatedAt: String = ""
):Serializable