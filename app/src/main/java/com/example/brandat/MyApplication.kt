package com.example.brandat

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import com.example.brandat.utils.Constants
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp()
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        PayPalCheckout.setConfig(
            CheckoutConfig(
                application = this,
                clientId = Constants.PAYPAL_CLIENT_ID,
                environment = Environment.SANDBOX,
                returnUrl = "com.example.brandat://paypalpay",
                currencyCode = CurrencyCode.EUR,
                userAction = UserAction.PAY_NOW,
                settingsConfig = SettingsConfig(
                    loggingEnabled = true,
                    shouldFailEligibility = false
                )
            )
        )


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setLocale()
    }
    private fun setLocale() {
        val resources: Resources = resources
        val configuration: Configuration = resources.getConfiguration()
        val locale: Locale = Locale("ar")
        if (configuration.locale != locale) {
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, null)
        }
    }


}