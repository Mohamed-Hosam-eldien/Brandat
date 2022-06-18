package com.example.brandat.models.orderModel

data class PriceSet(
    val presentment_money: PresentmentMoney = PresentmentMoney(),
    val shop_money: ShopMoney = ShopMoney()
)