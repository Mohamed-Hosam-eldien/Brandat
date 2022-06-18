package com.example.brandat.data.source.local

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.brandat.data.source.local.db.BrandatDao
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.Favourite
import com.example.brandat.ui.fragments.cart.Cart
import javax.inject.Inject

class LocalDataSource @Inject constructor(private var brandatDao:BrandatDao) :ILocalDataSource {
    override suspend fun insertFavouriteProduct(favourite: Favourite) {
        brandatDao.insertFavouriteProduct(favourite)
    }

    override suspend fun removeFavouriteProduct(productId:Long) {
         brandatDao.removeFavouriteProduct(productId)
    }

    override suspend fun getFavouriteProducts():List<Favourite> {
          return brandatDao.getFavouriteProducts()
    }

    override suspend fun searchInFavouriteProducts(productName: String): Favourite {
       return  brandatDao.searchInFavouriteProducts(productName)
    }


    override suspend fun insertAddress(customerAddress: CustomerAddress?) {
            customerAddress?.let {
                brandatDao.insertAddress(it)
            }
    }

    override suspend fun getAllAddresses(): List<CustomerAddress> {
        return  brandatDao.getAllAddresses()
    }

    override suspend fun removeAddress(city: String) {
        brandatDao.removeAddress(city)
    }

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

    override suspend fun updateOrder(product: Cart) {
        brandatDao.updateOrder(product.pQuantity,product.pId,product.tPrice)
    }

    override suspend fun getAllPrice(): Double {
        return if(brandatDao.getAllPrice() != null)
            brandatDao.getAllPrice()
        else
            0.0
    }

    override suspend fun isAdded(productName: String?): Cart {
        return brandatDao.isOrderd(productName)
        println("result====${brandatDao.isOrderd(productName)}")
    }

}