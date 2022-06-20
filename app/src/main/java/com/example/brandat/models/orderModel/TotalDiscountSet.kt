package com.example.brandat.models.orderModel

data class TotalDiscountSet(
    val presentment_money: PresentmentMoney = PresentmentMoney(),
    val shop_money: ShopMoney = ShopMoney()
)