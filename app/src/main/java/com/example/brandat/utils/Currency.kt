package com.example.brandat.utils

import android.app.Application
import android.content.Context
import com.example.brandat.utils.Constants.Companion.CURRENCY_TYPE

enum class CurrenciesEnum {
    EGP, USD, SAR
}

fun convertCurrency(value: Double?, context: Context): String {
    var price: String = ""
    val application = context.applicationContext as Application
    var sharedPreferences =
        application.getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)
    val currency = sharedPreferences.getString(CURRENCY_TYPE, "USD")
    if (currency == CurrenciesEnum.USD.toString()) {
        price = value.toString()
    } else if (currency == CurrenciesEnum.EGP.toString()) {
        price = ((value ?: 0.0) * 18).toString()
    }
    return price
}

