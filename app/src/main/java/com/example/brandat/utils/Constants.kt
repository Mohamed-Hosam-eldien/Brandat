package com.example.brandat.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.brandat.R
import com.example.brandat.models.Customer
import com.example.brandat.models.orderModel.discount.PriceRule
import com.example.brandat.ui.ProfileActivity
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

class Constants {

    companion object {
        var totalPrice: Double? = 0.0
        var discounCde: List<PriceRule>? = null
        var getDiscount :Boolean = false
        const val PAYPAL_CLIENT_ID = "AT0rbijoRD0wfeS-jvgy67hXA_HzxMnLgn56IztMCLsqqkpxShTzdzWL7at-St_2ExP9mhhcpykB_OS8"
        const val  SHARD_NAME = "shard"
        const val CURRENCY_TYPE = "currency"

        private const val apiKey = "54e7ce1d28a9d3b395830ea17be70ae1"
        private const val hostName = "mad-ism2022.myshopify.com"
        private const val password = "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"

        val EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        var user: Customer = Customer()
        var count: Int = 0
        const val BASE_URL = "https://${apiKey}:${password}@${hostName}/admin/api/2022-04/"

       fun Fragment.showSnackBar(it: String) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG)
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
                    resources.getColor(
                        R.color.black2
                    )
                )
                .setActionTextColor(resources.getColor(R.color.white)).setAction(getString(R.string.close)) {
                }.show()
        }
        fun Fragment.showDialogToRegister(title:String) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton(getString(R.string.login_now)) { _, _ ->
                startActivity(Intent(requireActivity(), ProfileActivity::class.java))
            }
            builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
            }
            builder.setTitle(title)
            builder.create().show()
        }
    }

}