package com.example.brandat.models


import com.google.gson.annotations.SerializedName

data class TotalDiscountsSet(
    @SerializedName("presentment_money")
    val presentmentMoney: PresentmentMoney,
    @SerializedName("shop_money")
    val shopMoney: ShopMoney
)