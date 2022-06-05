package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class LineItem(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("discount_allocations")
    val discountAllocations: List<Any>,
    @SerializedName("duties")
    val duties: List<Any>,
    @SerializedName("fulfillable_quantity")
    val fulfillableQuantity: Int,
    @SerializedName("fulfillment_service")
    val fulfillmentService: String,
    @SerializedName("fulfillment_status")
    val fulfillmentStatus: Any,
    @SerializedName("gift_card")
    val giftCard: Boolean,
    @SerializedName("grams")
    val grams: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_exists")
    val productExists: Boolean,
    @SerializedName("product_id")
    val productId: Long,
    @SerializedName("properties")
    val properties: List<Any>,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("requires_shipping")
    val requiresShipping: Boolean,
    @SerializedName("sku")
    val sku: String,
    @SerializedName("tax_lines")
    val taxLines: List<Any>,
    @SerializedName("taxable")
    val taxable: Boolean,
    @SerializedName("title")
    val title: String,
    @SerializedName("total_discount")
    val totalDiscount: String,
    @SerializedName("vendor")
    val vendor: String
)