package com.example.brandat.data.repos.products

import androidx.lifecycle.LiveData
import com.example.brandat.models.Brands
import com.example.brandat.models.Favourite
import com.example.brandat.models.Products
import retrofit2.Response

interface IProductsRepository {

    suspend fun insertFavouriteProduct(favourite: Favourite)
    suspend fun removeFavouriteProduct(productName:String)
    suspend fun getFavouriteProducts():List<Favourite>
    suspend fun  searchInFavouriteProducts(productName:String):Favourite
    suspend fun getbrand(): Response<Brands>
    suspend fun getCategories(productId: Long): Response<Products>

}