package com.example.brandat.models

import com.google.gson.annotations.SerializedName

data class CustomerRegisterModel (
    @SerializedName("customer")
    val customer: Customer
)