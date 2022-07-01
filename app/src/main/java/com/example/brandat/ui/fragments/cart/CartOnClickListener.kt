package com.example.brandat.ui.fragments.cart


interface CartOnClickListener {
    fun onClicked(order: Cart)
    fun onDeleteClicked(order: Cart)
    fun onPluseMinusClicked(count: Int, pId: Cart)
}