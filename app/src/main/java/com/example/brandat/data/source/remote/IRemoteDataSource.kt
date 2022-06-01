package com.example.brandat.data.source.remote

import com.example.brandat.models.Product
import com.example.brandat.models.Products

import com.example.brandat.models.Brand
import com.example.brandat.models.Brands
import retrofit2.Response

interface IRemoteDataSource {

    suspend fun getCategories(productId: Long) : Response<Products>
suspend fun getBrands(): Response<Brands>


}