package com.example.brandat.utils

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.brandat.utils.Constants.Companion.CURRENCY_TYPE
import java.text.DecimalFormat

enum class CurrenciesEnum {
    EGP, USD, SAR
}

fun convertCurrency(value: Double?, context: Context): String {
    var price: String = ""
    val application = context.applicationContext as Application
    var sharedPreferences =
        application.getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)
    val currency = sharedPreferences.getString(CURRENCY_TYPE, "EGP")
    if (currency == CurrenciesEnum.EGP.toString()) {
        price = value.toString()
    } else if (currency == CurrenciesEnum.USD.toString()) {
        var pprice = ((value ?: 0.0) / 18).toString()
        price  = DecimalFormat("####.00").format(pprice.toDouble()).toString()

    }
    return price
}

