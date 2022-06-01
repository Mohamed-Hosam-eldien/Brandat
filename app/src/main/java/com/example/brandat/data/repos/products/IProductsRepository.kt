package com.example.brandat.data.repos.products

import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.mymodel.MyProducts
import retrofit2.Response


interface IProductsRepository {

    suspend fun getCategories(productId: Long): Response<Products>


}