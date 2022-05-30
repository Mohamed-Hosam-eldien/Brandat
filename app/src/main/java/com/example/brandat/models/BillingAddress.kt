package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class BillingAddress(
    @SerializedName("address1")
    val address1: String,
    @SerializedName("address2")
    val address2: Any,
    @SerializedName("city")
    val city: String,
    @SerializedName("company")
    val company: Any,
    @SerializedName("country")
    val country: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("latitude")
    val latitude: Any,
    @SerializedName("longitude")
    val longitude: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("province")
    val province: String,
    @SerializedName("province_code")
    val provinceCode: Any,
    @SerializedName("zip")
    val zip: String
)