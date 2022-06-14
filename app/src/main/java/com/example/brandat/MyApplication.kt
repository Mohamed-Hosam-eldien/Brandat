package com.example.brandat

import android.app.Application
import com.example.brandat.utils.Constants
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp()
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        PayPalCheckout.setConfig(
            CheckoutConfig(
                application = this,
                clientId = Constants.PAYPAL_CLIENT_ID,
                environment = Environment.SANDBOX,
                returnUrl = BuildConfig.APPLICATION_ID + "://paypalpay",
                currencyCode = CurrencyCode.USD,
                userAction = UserAction.PAY_NOW,
                settingsConfig = SettingsConfig(
                    loggingEnabled = true
                )
            )
        )


    }

}