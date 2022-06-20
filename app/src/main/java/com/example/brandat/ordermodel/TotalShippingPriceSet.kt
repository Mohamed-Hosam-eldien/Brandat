package com.example.brandat.models.orderModel

import com.example.brandat.test.PresentmentMoney

data class TotalShippingPriceSet(
    val presentment_money: PresentmentMoney = PresentmentMoney(),
//    val shop_money: ShopMoneyXXXXXXXXXX = ShopMoneyXXXXXXXXXX()
)