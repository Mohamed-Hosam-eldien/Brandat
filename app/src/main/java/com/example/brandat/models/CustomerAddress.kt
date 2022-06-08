package com.example.brandat.models


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CustomerAddress(

    @SerializedName("address1")
    val address1: String,
    @PrimaryKey
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String
)


//    @SerializedName("latitude")
//    val latitude: Any,
//    @SerializedName("longitude")
//    val longitude: Any
//    @SerializedName("country_code")
//    val countryCode: String,
//    @SerializedName("first_name")
//    val firstName: String,
//    @SerializedName("last_name")
//    val lastName: String,
//    @SerializedName("name")
//    val name: String,
//    @SerializedName("phone")
//    val phone: String,
//    @SerializedName("province")
//    val province: String,
//    @SerializedName("province_code")
//    val provinceCode: Any,
//    @SerializedName("address2")
//    val address2: Any,
//    @SerializedName("company")
//    val company: Any,
//    @SerializedName("zip")
//    val zip: String
