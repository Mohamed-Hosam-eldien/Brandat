package com.example.brandat.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Constants {

    companion object{

        private const val apiKey = "54e7ce1d28a9d3b395830ea17be70ae1"
        private const val hostName = "mad-ism2022.myshopify.com"
        private const val password = "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"


        const val BASE_URL = "https://${apiKey}:${password}@${hostName}/admin/api/2022-04/"


        fun hasInternetConnection(context:Context): Boolean {

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