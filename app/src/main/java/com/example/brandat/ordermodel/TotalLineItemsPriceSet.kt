package com.example.brandat.models.orderModel

import com.example.brandat.test.PresentmentMoney

data class TotalLineItemsPriceSet(
    val presentment_money: PresentmentMoney = PresentmentMoney(),
//    val shop_money: ShopMoneyXXXXXXXX = ShopMoneyXXXXXXXX()
)