package com.example.brandat.models.orderModel

data class DiscountCode(
    val code: String = "",
    val created_at: String = "",
    val id: Long = 0,
    val price_rule_id: Long = 0,
    val updated_at: String = "",
    val usage_count: Int = 0
)