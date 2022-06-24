package com.example.brandat.data.source

import androidx.lifecycle.MutableLiveData
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.Favourite
import com.example.brandat.ui.fragments.cart.Cart

class FakeLocalDataSource():ILocalDataSource {

    var _addresses = mutableListOf(CustomerAddress(), CustomerAddress(), CustomerAddress())
    val addresses : List<CustomerAddress> = _addresses

    private val observableAddressItems = MutableLiveData<List<CustomerAddress>>()
    private val observableTotalPrice = MutableLiveData<Float>()


    override suspend fun insertAddress(customerAddress: CustomerAddress?) {
        _addresses.add(customerAddress!!)
    }

    override suspend fun removeAddress(city: String) {
        _addresses.clear()
    }


    override suspend fun getAllAddresses(): List<CustomerAddress> {
        return addresses
    }


    override suspend fun insertFavouriteProduct(favourite: Favourite) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavouriteProduct(productId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavouriteProducts(): List<Favourite> {
        TODO("Not yet implemented")
    }

    override suspend fun searchInFavouriteProducts(productName: String): Favourite {
        TODO("Not yet implemented")
    }

    override suspend fun addProductToCart(product: Cart) {
        TODO("Not yet implemented")
    }

    override suspend fun removeProductFromCart(product: Cart) {
        TODO("Not yet implemented")
    }

    override suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCartProducts(): List<Cart> {
        TODO("Not yet implemented")
    }

    override suspend fun updateOrder(product: Cart) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPrice(): Double {
        TODO("Not yet implemented")
    }
}