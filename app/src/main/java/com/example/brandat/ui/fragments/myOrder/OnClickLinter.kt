package com.example.brandat.ui.fragments.myOrder

import com.example.brandat.models.orderModel.Order

interface OnItemClickLinter {
    fun onClick(orderItem: Order)
}