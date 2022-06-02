package com.example.brandat.data.repos.products

import androidx.lifecycle.LiveData
import com.example.brandat.models.Favourite

interface IProductsRepository {

    suspend fun insertFavouriteProduct(favourite: Favourite)
    suspend fun removeFavouriteProduct(productName:String)
    suspend fun getFavouriteProducts():List<Favourite>
    suspend fun  searchInFavouriteProducts(productName:String):Favourite


}