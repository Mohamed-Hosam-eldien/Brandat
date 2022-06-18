package com.example.brandat.models.orderModel

data class CurrentTotalDiscountsSet(
    val presentment_money: PresentmentMoney = PresentmentMoney(),
    val shop_money: ShopMoney = ShopMoney()
)