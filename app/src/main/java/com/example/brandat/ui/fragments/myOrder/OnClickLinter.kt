package com.example.brandat.ui.fragments.myOrder

import android.view.View
import android.widget.Button
import com.example.brandat.models.orderModel.Order

interface OnItemClickLinter {
    fun onClick(orderItem: Order)

}