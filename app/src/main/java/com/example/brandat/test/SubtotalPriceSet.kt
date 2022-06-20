package com.example.brandat.test


import com.example.brandat.test.PresentmentMoney
import com.example.brandat.test.ShopMoney

data class SubtotalPriceSet(
    val presentment_money: PresentmentMoney = PresentmentMoney(),
    val shop_money: ShopMoney = ShopMoney()
)