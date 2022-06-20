package com.example.brandat.models.draftOrder

import com.google.gson.annotations.SerializedName

data class LineItem(
//    val admin_graphql_api_id: String,
//    val applied_discount: Any,
//    val custom: Boolean,
//    val fulfillment_service: String,
//    val gift_card: Boolean,
//    val grams: Int,
//    val id: Long,
//    val name: String,
//    val price: String,
//    val product_id: Long,
//    val properties: List<Any>,
//    val quantity: Int,
//    val requires_shipping: Boolean,
//    val sku: String,
//    val tax_lines: List<TaxLine>,
//    val taxable: Boolean,
//    val title: String,
//    val variant_id: Long,
//    val variant_title: String,
//    val vendor: String

//    @SerializedName("admin_graphql_api_id")
//    val adminGraphqlApiId: String = "",
    @SerializedName("variant_id")
    val variant_id: Long,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("name")
    val name: String = ""
//    @SerializedName("variant_title")
//    val variant_title: String = "",
//    @SerializedName("discount_allocations")
//    val discountAllocations: List<Any> = emptyList(),
//    @SerializedName("duties")
//    val duties: List<Any> = emptyList(),
//    @SerializedName("fulfillable_quantity")
//    val fulfillableQuantity: Int = 0,
//    @SerializedName("applied_discount")
//    val applied_discount: Int = 0,
//    @SerializedName("fulfillment_service")
//    val fulfillmentService: String = "",
//    @SerializedName("fulfillment_status")
//    val fulfillmentStatus: Any  = "",
//    @SerializedName("gift_card")
//    val giftCard: Boolean = false,
//    @SerializedName("custom")
//    val custom: Boolean = false,
//    @SerializedName("grams")
//    val grams: Int=0,
//    @SerializedName("id")
//    val id: Long=0,
//    @SerializedName("name")
//    val name: String="",
//    @SerializedName("price")
//    val price: String,
//    @SerializedName("product_exists")
//    val productExists: Boolean = false,
//    @SerializedName("product_id")
//    val productId: Long = 0,
//    @SerializedName("properties")
//    val properties: List<Any>  = emptyList(),

//    @SerializedName("requires_shipping")
//    val requiresShipping: Boolean = false,
//    @SerializedName("sku")
//    val sku: String = "",
//    @SerializedName("tax_lines")
//    val taxLines: List<Any> = emptyList(),
//    @SerializedName("taxable")
//    val taxable: Boolean=false,
//    @SerializedName("title")
//    val title: String,
//    @SerializedName("total_discount")
//    val totalDiscount: String="",
//    @SerializedName("vendor")
//    val vendor: String
)