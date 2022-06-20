package com.example.brandat.models.orderModel

import com.example.brandat.test.PresentmentMoney
import com.example.brandat.test.ShopMoney

data class CurrentSubtotalPriceSet(
    val presentment_money: PresentmentMoney = PresentmentMoney(),
    val shop_money: ShopMoney = ShopMoney()
)