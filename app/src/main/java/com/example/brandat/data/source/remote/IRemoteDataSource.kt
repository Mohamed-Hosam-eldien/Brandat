package com.example.brandat.data.source.remote

import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.mymodel.MyProducts
import retrofit2.Response

interface IRemoteDataSource {

    suspend fun getCategories(productId: Long) : Response<Products>

}