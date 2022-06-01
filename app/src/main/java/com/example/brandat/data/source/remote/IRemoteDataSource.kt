package com.example.brandat.data.source.remote

import com.example.brandat.models.Product
import com.example.brandat.models.Products
import retrofit2.Response

interface IRemoteDataSource {

    suspend fun getProductDetails(productId:Long): Response<Product>

}