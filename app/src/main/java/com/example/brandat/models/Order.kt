package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class Order(

    @SerializedName( "id")
    val id: Long? = 0,

    @SerializedName( "email")
    val email: String? = "",

    @SerializedName( "billing_address")
    val billingAddress: BillingAddress? = null,

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
    val line_items: List<LineItem>? = listOf(),

    @SerializedName("gateway")
    val gateway: String = "",
        @SerializedName("source_name")
        val sourceName: String="",
//        @SerializedName("currency")
//        val currency: String
//    @SerializedName("admin_graphql_api_id")
//    val adminGraphqlApiId: String,
//    @SerializedName("app_id")
//    val appId: Int,
//    @SerializedName("billing_address")
//    val billingAddress: BillingAddress,
//    @SerializedName("buyer_accepts_marketing")
//    val buyerAcceptsMarketing: Boolean,
//    @SerializedName("cart_token")
//    val cartToken: Any,
//    @SerializedName("checkout_id")
//    val checkoutId: Any,
//    @SerializedName("checkout_token")
//    val checkoutToken: Any,
//    @SerializedName("closed_at")
//    val closedAt: Any,
//    @SerializedName("confirmed")
//    val confirmed: Boolean,
//    @SerializedName("contact_email")
//    val contactEmail: String,
//    @SerializedName("created_at")
//    val createdAt: String,

//    @SerializedName("current_subtotal_price")
//    val currentSubtotalPrice: String,
//    @SerializedName("current_subtotal_price_set")
//    val currentSubtotalPriceSet: CurrentSubtotalPriceSet,
//    @SerializedName("current_total_discounts")
//    val currentTotalDiscounts: String,
////    @SerializedName("current_total_discounts_set")
////    val currentTotalDiscountsSet: CurrentTotalDiscountsSet,
//    @SerializedName("current_total_duties_set")
//    val currentTotalDutiesSet: Any,
//    @SerializedName("current_total_price")
//    val currentTotalPrice: String,
////    @SerializedName("current_total_price_set")
////    val currentTotalPriceSet: com.example.brandat.models.orderModel.CurrentTotalPriceSet,
//    @SerializedName("current_total_tax")
//    val currentTotalTax: String,
//    @SerializedName("current_total_tax_set")
////    val currentTotalTaxSet: CurrentTotalTaxSet,
////    @SerializedName("customer")
//    val customer: Customer,
//    @SerializedName("customer_locale")
//    val customerLocale: Any,
//    @SerializedName("device_id")
//    val deviceId: Any,
//    @SerializedName("discount_applications")
//    val discountApplications: List<Any>,
//    @SerializedName("discount_codes")
//    val discountCodes: List<Any>,
//    @SerializedName("email")
//    val email: String,
//    @SerializedName("estimated_taxes")
//    val estimatedTaxes: Boolean,
//    @SerializedName("financial_status")
//    val financialStatus: String,
//    @SerializedName("fulfillment_status")
//    val fulfillmentStatus: Any,
//    @SerializedName("fulfillments")
//    val fulfillments: List<Any>,
//    @SerializedName("gateway")
//    val gateway: String,
//    @SerializedName("id")
//    val id: Long,
//    @SerializedName("landing_site")
//    val landingSite: Any,
//    @SerializedName("landing_site_ref")
//    val landingSiteRef: Any,
//    @SerializedName("line_items")
//    val lineItems: List<LineItem>,
//    @SerializedName("location_id")
//    val locationId: Any,
//    @SerializedName("name")
//    val name: String,
//    @SerializedName("note")
//    val note: Any,
//    @SerializedName("note_attributes")
//    val noteAttributes: List<Any>,
//    @SerializedName("number")
//    val number: Int,
//    @SerializedName("order_number")
//    val orderNumber: Int,
//    @SerializedName("order_status_url")
//    val orderStatusUrl: String,
//    @SerializedName("original_total_duties_set")
//    val originalTotalDutiesSet: Any,
//    @SerializedName("payment_gateway_names")
//    val paymentGatewayNames: List<Any>,
//    @SerializedName("payment_terms")
//    val paymentTerms: Any,
//    @SerializedName("phone")
//    val phone: Any,
//    @SerializedName("presentment_currency")
//    val presentmentCurrency: String,
//    @SerializedName("processed_at")
//    val processedAt: String,
//    @SerializedName("processing_method")
//    val processingMethod: String,
//    @SerializedName("reference")
//    val reference: Any,
//    @SerializedName("referring_site")
//    val referringSite: Any,
//    @SerializedName("refunds")
//    val refunds: List<Any>,
//    @SerializedName("shipping_address")
//    val customerAddress: CustomerAddress,
//    @SerializedName("shipping_lines")
//    val shippingLines: List<Any>,
//    @SerializedName("source_identifier")
//    val sourceIdentifier: Any,
//    @SerializedName("source_name")
//    val sourceName: String,
//    @SerializedName("source_url")
//    val sourceUrl: Any,
//    @SerializedName("subtotal_price")
//    val subtotalPrice: String,
//    @SerializedName("tags")
//    val tags: String,
//    @SerializedName("tax_lines")
//    val taxLines: List<Any>,
//    @SerializedName("taxes_included")
//    val taxesIncluded: Boolean,
//    @SerializedName("test")
//    val test: Boolean,
//    @SerializedName("token")
//    val token: String,
//    @SerializedName("total_discounts")
//    val totalDiscounts: String,
//    @SerializedName("total_discounts_set")
//    val totalDiscountsSet: TotalDiscountsSet,
//    @SerializedName("total_line_items_price")
//    val totalLineItemsPrice: String,
//    @SerializedName("total_outstanding")
//    val totalOutstanding: String,
//    @SerializedName("total_price")
//    val totalPrice: String,
//    @SerializedName("total_price_usd")
//    val totalPriceUsd: String,
//    @SerializedName("total_tax")
//    val totalTax: String,
//    @SerializedName("total_tip_received")
//    val totalTipReceived: String,
//    @SerializedName("total_weight")
//    val totalWeight: Int,
//    @SerializedName("updated_at")
//    val updatedAt: String,
//    @SerializedName("user_id")
//    val userId: Any
)