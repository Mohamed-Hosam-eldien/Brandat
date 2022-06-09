
package com.example.brandat.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DefaultAddress(
    @SerializedName("address1")
    val address1: String,
    @SerializedName("address2")
    val address2: String = "",
    @SerializedName("city")
    val city: String,
    @SerializedName("company")
    val company: String = "",
    @SerializedName("country")
    val country: String="",
    @SerializedName("country_code")
    val countryCode: String="",
    @SerializedName("country_name")
    val countryName: String="",
    @SerializedName("customer_id")
    val customerId: Long=0,
    @SerializedName("default")
    val default: Boolean=true,
    @SerializedName("first_name")
    val firstName: String="",
    @SerializedName("id")
    val id: Long=0,
    @SerializedName("last_name")
    val lastName: String="",
    @SerializedName("name")
    val name: String="",
    @SerializedName("phone")
    val phone: String="",
    @SerializedName("province")
    val province: String="",
    @SerializedName("province_code")
    val provinceCode: String="",
    @SerializedName("zip")
    val zip: String=""
) : Parcelable