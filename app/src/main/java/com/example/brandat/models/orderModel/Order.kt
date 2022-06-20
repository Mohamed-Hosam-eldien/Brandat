package com.example.brandat.models.orderModel

import com.google.gson.annotations.SerializedName

data class Order(


    @SerializedName( "id")
    val id: Long? = 0,
    @SerializedName( "total_price")
    val total_price: String = "",

    @SerializedName( "email")
    val email: String? = "",

    @SerializedName( "billing_address")
    val billingAddress: BillingAddress?,

    @SerializedName( "app_id")
    val orderNumber: Long? = 0,

    @SerializedName( "created_at")
    val createdAt: String? = "",

    @SerializedName( "current_subtotal_price")
    val finalPrice: String? = "",

    @SerializedName( "current_total_discounts")
    val totalDiscount: String? = "",


    @SerializedName( "quantity")
    val quantity: Long? = 0,

    @SerializedName( "financial_status")
    val financialStatus: String? = "",

    @SerializedName( "line_items")
    val items: List<LineItem>? = listOf(),


//    val admin_graphql_api_id: String = "",
//    val app_id: Int = 1,
//    val billing_address: BillingAddress = BillingAddress(),
//    val browser_ip: Any = Any(),
//    val buyer_accepts_marketing: Boolean = false,
//    val cancel_reason: Any = Any(),
//    val cancelled_at: Any = Any(),
//    val cart_token: Any = Any(),
//    val checkout_id: Any = Any(),
//    val checkout_token: Any = Any(),
//    val closed_at: Any = Any(),
//    val confirmed: Boolean = false,
//    val contact_email: String = "",
//    val created_at: String = "",
//    val currency: String = "",
//    val current_subtotal_price: String = "",
//    val current_subtotal_price_set: CurrentSubtotalPriceSet = CurrentSubtotalPriceSet(),
//    val current_total_discounts: String = "",
//    val current_total_discounts_set: CurrentTotalDiscountsSet = CurrentTotalDiscountsSet(),
//    val current_total_duties_set: Any = Any(),
//    val current_total_price: String = "",
//    val current_total_price_set: CurrentTotalPriceSet = CurrentTotalPriceSet(),
//    val current_total_tax: String = "",
//    val current_total_tax_set: CurrentTotalTaxSet = CurrentTotalTaxSet(),
//    val customer: Customer = Customer(),
//    val customer_locale: Any = Any(),
//    val device_id: Any = Any(),
//    val discount_applications: List<Any> = listOf(),
//    val discount_codes: List<Any> = listOf(),
//    var email: String = "",
//    val estimated_taxes: Boolean = false,
//    val financial_status: String = "",
//    var fulfillment_status: Any = Any(),
//    val fulfillments: List<Any> = listOf(),
//    val gateway: String = "",
//    val id: Long = 0,
//    val landing_site: Any = Any(),
//    val landing_site_ref: Any = Any(),
//    var line_items: List<LineItem> = listOf(),
//    val location_id: Any = Any(),
//    val name: String = "",
//    val note: Any = Any(),
//    val note_attributes: List<Any> = listOf(),
//    val number: Int = 0,
//    val order_number: Int = 0,
//    val order_status_url: String = "",
//    val original_total_duties_set: Any = Any(),
//    val payment_gateway_names: List<Any> = listOf(),
//    val payment_terms: Any = Any(),
//    val phone: Any = Any(),
//    val presentment_currency: String = "",
//    val processed_at: String = "",
//    val processing_method: String = "",
//    val reference: Any = Any(),
//    val referring_site: Any = Any(),
//    val refunds: List<Any> = listOf(),
//    val shipping_address: ShippingAddress = ShippingAddress(),
//    val shipping_lines: List<Any> = listOf(),
//    val source_identifier: Any = Any(),
//    val source_name: String = "",
//    val source_url: Any = Any(),
//    val subtotal_price: String = "",
//    val subtotal_price_set: SubtotalPriceSet = SubtotalPriceSet(),
//    val tags: String = "",
//    val tax_lines: List<Any> = listOf(),
//    val taxes_included: Boolean = false,
//    val test: Boolean = false,
//    val token: String = "",
//    val total_discounts: String = "",
//    val total_discounts_set: TotalDiscountsSet = TotalDiscountsSet(),
//    val total_line_items_price: String = "",
//    val total_line_items_price_set: TotalLineItemsPriceSet = TotalLineItemsPriceSet(),
//    val total_outstanding: String = "",
//    val total_price_set: TotalPriceSet = TotalPriceSet(),
//    val total_price_usd: String = "",
//    val total_shipping_price_set: TotalShippingPriceSet = TotalShippingPriceSet(),
//    val total_tax: String = "",
//    val total_tax_set: TotalTaxSet = TotalTaxSet(),
//    val total_tip_received: String = "",
//    val total_weight: Int = 0,
//    val updated_at: String = "",
//    val user_id: Any = Any()
)