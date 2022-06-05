package com.example.brandat.data.repos.products

import com.example.brandat.models.Brands
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import retrofit2.Response


interface IProductsRepository {

    suspend fun getCategories(productId: Long): Response<Products>

    suspend fun getbrand(): Response<Brands>

    suspend fun getAllProduct(): Response<Products>

    suspend fun getAllProductByType(type:String): Response<Products>

}