package com.example.brandat.test

data class CurrentTotalTaxSet(
    val presentment_money: PresentmentMoney = PresentmentMoney(),
    val shop_money: ShopMoney = ShopMoney()
)