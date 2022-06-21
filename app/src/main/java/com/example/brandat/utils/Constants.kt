package com.example.brandat.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.brandat.models.Customer
import com.example.brandat.models.orderModel.discount.PriceRule
import java.util.regex.Pattern

class Constants {

    companion object {
        const val CURRENCY_CODE = "currency_code"
        const val CURRENCY_VALUE = "currency_value"
        var totalPrice: Double? = 0.0
        var discounCde: List<PriceRule>? = null
        const val PAYPAL_CLIENT_ID = "Aab9pWmlaqabofcahFzc-e1zxOzjqQqrY1YREcrpdIWR6pHJWJF3SfhtZ0rnZq4iahcfu6xG8AFBP1hL"
        private const val apiKey = "54e7ce1d28a9d3b395830ea17be70ae1"
        private const val hostName = "mad-ism2022.myshopify.com"
        private const val password = "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"
     private  const val apiKeyCurrency ="345d0e86dea309b5506b82a5"
        const val  SHARD_NAME = "shard"
        const val CURRENCY_TYPE = "currency"
//          const val apiKeyCurrency ="POiql13RXSALs8fvH93U3sfUERn1ygWx"


        val EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        lateinit var user:Customer

        const val BASE_URL = "https://${apiKey}:${password}@${hostName}/admin/api/2022-04/"
        const val  CURRENCY_BASE_URL = "https://v6.exchangerate-api.com/"

        fun hasInternetConnection(context: Context): Boolean {

            val connectivityManager = context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

            val actionNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(actionNetwork) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }

        }

    }

}