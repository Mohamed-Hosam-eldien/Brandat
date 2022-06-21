package com.example.brandat.models.currency

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("base_code")
    val base_code: String ?,
    @SerializedName("conversion_rates")
    val conversion_rates: ConversionRates ?
//    val documentation: String = "",
//    @SerializedName("")
//    val result: String?,
//    val terms_of_use: String = "",
//    val time_last_update_unix: Int = 0,
//    val time_last_update_utc: String = "",
//    val time_next_update_unix: Int = 0,
//    val time_next_update_utc: String = ""
)
//@SerializedName("base_code")
//val baseCode: String?,
//@SerializedName("conversion_rates")
//val conversionRates: ConversionRates