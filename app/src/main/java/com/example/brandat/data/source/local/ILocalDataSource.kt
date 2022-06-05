package com.example.brandat.data.source.local

import com.example.brandat.ui.fragments.cart.Cart

interface ILocalDataSource {

    suspend fun addProductToCart(product: Cart)
    suspend fun removeProductFromCart(product: Cart)
    suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>)
    suspend fun getAllCartProducts(): List<Cart>
    suspend fun updateOrder(product: Cart)

}