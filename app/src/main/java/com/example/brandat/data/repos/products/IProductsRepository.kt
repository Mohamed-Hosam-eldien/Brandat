package com.example.brandat.data.repos.products

import com.example.brandat.models.Product
import com.example.brandat.models.Products
import retrofit2.Response
//@ActivityRetainedScoped
interface IProductsRepository {

    suspend fun getProductDetails(productId:Long):Response<Product>

}