package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class PriceSet(
    @SerializedName("presentment_money")
    val presentmentMoney: PresentmentMoneyXXXX,
    @SerializedName("shop_money")
    val shopMoney: ShopMoneyXXXX
)