package com.example.brandat.data.source.local

import androidx.lifecycle.LiveData
import com.example.brandat.models.Favourite

import com.example.brandat.models.CustomerAddress

import com.example.brandat.ui.fragments.cart.Cart

interface ILocalDataSource {

    suspend fun insertFavouriteProduct(favourite: Favourite)
    suspend fun removeFavouriteProduct(productId:Long)
    suspend fun getFavouriteProducts(): List<Favourite>
    suspend fun  searchInFavouriteProducts(productName:String):Favourite


     suspend fun insertAddress(customerAddress: CustomerAddress?)
     suspend fun getAllAddresses():List<CustomerAddress>
     suspend fun removeAddress(city:String)
    suspend fun addProductToCart(product: Cart)
    suspend fun removeProductFromCart(product: Cart)
    suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>)
    suspend fun getAllCartProducts(): List<Cart>
    suspend fun updateOrder(product: Cart)

}