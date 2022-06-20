package com.example.brandat.models.orderModel

data class CurrentTotalTaxSet(
    val presentment_money: PresentmentMoney = PresentmentMoney(),
    val shop_money: ShopMoney = ShopMoney()
)