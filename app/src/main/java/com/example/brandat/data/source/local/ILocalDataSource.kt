package com.example.brandat.data.source.local

import androidx.lifecycle.LiveData
import com.example.brandat.models.Favourite

interface ILocalDataSource {

    suspend fun insertFavouriteProduct(favourite: Favourite)
    suspend fun removeFavouriteProduct(productName:String)
    suspend fun getFavouriteProducts(): List<Favourite>
    suspend fun  searchInFavouriteProducts(productName:String):Favourite



}