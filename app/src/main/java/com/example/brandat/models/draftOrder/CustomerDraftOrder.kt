package com.example.brandat.models.draftOrder

import com.google.gson.annotations.SerializedName

data class CustomerDraftOrder(
    @SerializedName("draft_orders")
    var draft_orders: DraftOrders
)

data class DraftOrders(
    @SerializedName("line_items")
    val line_items: List<LineItem> = listOf()
)