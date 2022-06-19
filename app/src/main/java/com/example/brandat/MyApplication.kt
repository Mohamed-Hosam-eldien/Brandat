package com.example.brandat

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp()
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
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