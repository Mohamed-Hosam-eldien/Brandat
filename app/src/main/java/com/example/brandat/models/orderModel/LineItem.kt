package com.example.brandat.models.orderModel

data class LineItem(
    val variant_id: Long ,
    val quantity: Int,
    val name: String = "",
    val sku: String = "",
    val price: String = "",
//    val admin_graphql_api_id: String = "",
//    val discount_allocations: List<Any> = listOf(),
//    val duties: List<Any> = listOf(),
//    val fulfillable_quantity: Int = 0,
//    val fulfillment_service: String = "",
//    val fulfillment_status: Any = Any(),
//    val gift_card: Boolean = false,
//    val grams: Int = 0,
//    val id: Long = 0,
//    val title: String = "",
//    val price_set: PriceSet = PriceSet(),
//    val product_exists: Boolean = false,
//    val product_id: Long = 0,
//    val properties: List<Any> = listOf(),
//    val requires_shipping: Boolean = false,
//    val tax_lines: List<Any> = listOf(),
//    val taxable: Boolean = false,
//    val total_discount: String = "",
//    val total_discount_set: TotalDiscountSet = TotalDiscountSet(),



//    val variant_inventory_management: String = "",
//    val variant_title: String = "",
//    val vendor: String = ""
)