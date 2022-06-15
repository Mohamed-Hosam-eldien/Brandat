package com.example.brandat.models.draftOrder

import com.google.gson.annotations.SerializedName

data class DraftOrderModel(
    @SerializedName("draft_orders")
    val draft_orders: DraftOrder
)