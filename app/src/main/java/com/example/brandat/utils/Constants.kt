package com.example.brandat.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
class Constants {
    companion object{

        private const val apiKey = "c48655414af1ada2cd256a6b5ee391be"
        private const val hostName = "mobile-ismailia.myshopify.com"
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