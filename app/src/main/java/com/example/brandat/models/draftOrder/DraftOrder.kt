package com.example.brandat.models.draftOrder

import com.example.brandat.models.draft.Customer
import com.google.gson.annotations.SerializedName

data class DraftOrder(
    @SerializedName("admin_graphql_api_id")
    val admin_graphql_api_id: String="",
    @SerializedName("applied_discount")
    val applied_discount: Any="",
    @SerializedName("billing_address")
    val billing_address: Any="",
    @SerializedName("completed_at")
    val completed_at: Any="",
    @SerializedName("created_at")
    val created_at: String="",
    @SerializedName("currency")
    val currency: String="",
    @SerializedName("customer")
    val customer: Customer,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Long=0,
    @SerializedName("invoice_sent_at")
    val invoice_sent_at: Any="",
    @SerializedName("invoice_url")
    val invoice_url: String="",
    @SerializedName("line_items")
    val line_items: List<LineItem>,
    @SerializedName("name")
    val name: String="",
    @SerializedName("note")
    val note: Any="",
    @SerializedName("note_attributes")
    val note_attributes: List<Any> = emptyList(),
    @SerializedName("order_id")
    val order_id: Any=0,
    @SerializedName("payment_terms")
    val payment_terms: Any="",
    @SerializedName("shipping_address")
    val shipping_address: Any="",
    @SerializedName("shipping_line")
    val shipping_line: Any="",
    @SerializedName("status")
    val status: String="open",
    @SerializedName("subtotal_price")
    val subtotal_price: String="600",
    @SerializedName("tags")
    val tags: String="",
    @SerializedName("tax_exempt")
    val tax_exempt: Boolean=false,
    @SerializedName("tax_lines")
    val tax_lines: List<TaxLineX> = emptyList(),
    @SerializedName("taxes_included")
    val taxes_included: Boolean = false,
    @SerializedName("total_price")
    val total_price: String = "600",
    @SerializedName("total_tax")
    val total_tax: String = "60",
    @SerializedName("updated_at")
    val updated_at: String = ""

)