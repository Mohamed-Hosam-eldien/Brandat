package com.example.brandat.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.brandat.models.Favourite
import com.example.brandat.models.Product

class FavouriteDiffUtil (

    private val oldList: List<Favourite>,
    private val newList: List<Favourite>
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