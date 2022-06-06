package com.example.brandat.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.brandat.models.Product
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.category.ProductModel

class ProductDiffUtil(
    private val oldList: List<ProductDetails>,
    private val newList: List<ProductDetails>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}