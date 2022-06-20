package com.example.brandat.models.orderModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BillingAddress(
    val address1: String = "",
    val address2: String = "",
    val city: String = "",
    val company: String= "" ,
    val country: String = "",
    val country_code: String = "",
    val first_name: String = "",
    val last_name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val name: String = "",
    val phone: String = "",
    val province: String = "",
    val province_code: String = "",
    val zip: String = ""
) : Parcelable