package com.example.brandat.data.source.local

import androidx.lifecycle.LiveData
import com.example.brandat.data.source.local.db.BrandatDao
import com.example.brandat.ui.fragments.cart.Cart
import javax.inject.Inject

class LocalDataSource @Inject constructor(private var brandatDao: BrandatDao) : ILocalDataSource {
    override suspend fun addProductToCart(product: Cart) {
        brandatDao.insertCartProduct(product)
    }

    override suspend fun removeProductFromCart(product: Cart) {
        brandatDao.removeProductFromCart(product)
    }

    override suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>) {
        brandatDao.removeSelectedProductsFromCart(product)
    }

    override suspend fun getAllCartProducts(): List<Cart> {
        return brandatDao.getAllCartProducts()
    }

}