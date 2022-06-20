package com.example.brandat.models


import com.google.gson.annotations.SerializedName

//////////////////
data class Variant(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String = "",
    @SerializedName("barcode")
    val barcode: Any = "",
    @SerializedName("compare_at_price")
    val compareAtPrice: Any = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("fulfillment_service")
    val fulfillmentService: String = "",
    @SerializedName("grams")
    val grams: Int = 0,
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("image_id")
    val imageId: Any = "",
    @SerializedName("inventory_item_id")
    val inventoryItemId: Long = 0,
    @SerializedName("inventory_management")
    val inventoryManagement: String = "",
    @SerializedName("inventory_policy")
    val inventoryPolicy: String = "",
    @SerializedName("inventory_quantity")
    val inventoryQuantity: Int = 0,
    @SerializedName("old_inventory_quantity")
    val oldInventoryQuantity: Int = 0,
    @SerializedName("option1")
    val option1: String = "",
    @SerializedName("option2")
    val option2: String = "",
    @SerializedName("option3")
    val option3: Any = "",
    @SerializedName("position")
    val position: Int = 0,
    @SerializedName("price")
    val price: String = "",
    @SerializedName("product_id")
    val productId: Long = 0,
    @SerializedName("requires_shipping")
    val requiresShipping: Boolean = false,
    @SerializedName("sku")
    val sku: String = "",
    @SerializedName("taxable")
    val taxable: Boolean = false,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("weight")
    val weight: Double = 0.0,
    @SerializedName("weight_unit")
    val weightUnit: String = ""
)