package com.example.brandat.data.source.local

import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.Favourite
import com.example.brandat.ui.fragments.cart.Cart

interface ILocalDataSource {

    suspend fun insertFavouriteProduct(favourite: Favourite)
    suspend fun removeFavouriteProduct(productId: Long)
    suspend fun getFavouriteProducts(): List<Favourite>
    suspend fun searchInFavouriteProducts(productName: String): Favourite


    suspend fun insertAddress(customerAddress: CustomerAddress?)
    suspend fun getAllAddresses(): List<CustomerAddress>
    suspend fun removeAddress(city: String)
    suspend fun addProductToCart(product: Cart)
    suspend fun removeProductFromCart(product: Cart)
    suspend fun removeSelectedProductsFromCart(product: List<Cart>)
    suspend fun getAllCartProducts(): List<Cart>
    suspend fun updateOrder(product: Cart)
    suspend fun getAllPrice(): Double
    suspend fun isAdded(productName: String?): Cart

}