package com.example.brandat.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.brandat.ui.fragments.cart.Cart

class CartDiffUtil(private val oldList: List<Cart>, private val newList: List<Cart>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size


    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}