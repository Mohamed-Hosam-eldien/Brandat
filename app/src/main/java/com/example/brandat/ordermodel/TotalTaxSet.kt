package com.example.brandat.models.orderModel

import com.example.brandat.test.PresentmentMoney

data class TotalTaxSet(
    val presentment_money: PresentmentMoney = PresentmentMoney(),
)