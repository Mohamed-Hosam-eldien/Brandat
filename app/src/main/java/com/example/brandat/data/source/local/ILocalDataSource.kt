package com.example.brandat.data.source.local

import com.example.brandat.models.CustomerAddress

import com.example.brandat.ui.fragments.cart.Cart

interface ILocalDataSource {

     suspend fun insertAddress(customerAddress: CustomerAddress?)
     suspend fun getAllAddresses():List<CustomerAddress>
     suspend fun removeAddress(city:String)
    suspend fun addProductToCart(product: Cart)
    suspend fun removeProductFromCart(product: Cart)
    suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>)
    suspend fun getAllCartProducts(): List<Cart>
    suspend fun updateOrder(product: Cart)

}