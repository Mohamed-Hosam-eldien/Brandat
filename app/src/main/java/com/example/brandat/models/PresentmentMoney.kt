package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class PresentmentMoney(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("currency_code")
    val currencyCode: String
)