package com.example.brandat.models

import com.google.gson.annotations.SerializedName

data class CustomerModel (
    @SerializedName( "customers")
    val customer: List<Customer>
)

