package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class SmsMarketingConsent(
    @SerializedName("consent_collected_from")
    val consentCollectedFrom: String,
    @SerializedName("consent_updated_at")
    val consentUpdatedAt: Any,
    @SerializedName("opt_in_level")
    val optInLevel: String,
    @SerializedName("state")
    val state: String
)