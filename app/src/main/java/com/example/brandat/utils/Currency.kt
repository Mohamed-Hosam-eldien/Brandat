package com.example.brandat.utils

import android.app.Application
import android.content.Context
import com.example.brandat.utils.Constants.Companion.CURRENCY_TYPE
import java.text.DecimalFormat

enum class CurrenciesEnum {
    EGP, USD, SAR
}

fun convertCurrency(value: Double?, context: Context): String {
    var price = ""
    val application = context.applicationContext as Application
    val sharedPreferences =
        application.getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)

    val currency = sharedPreferences.getString(CURRENCY_TYPE, "EGP")

    if (currency == CurrenciesEnum.EGP.toString()) {
        price = value.toString()
    } else if (currency == CurrenciesEnum.USD.toString()) {
        val pprice = ((value ?: 0.0) / 18).toString()
        price  = DecimalFormat("####.00").format(pprice.toDouble()).toString()
    }

    return DecimalFormat("####.00").format(price.toDouble()).toString()
}

