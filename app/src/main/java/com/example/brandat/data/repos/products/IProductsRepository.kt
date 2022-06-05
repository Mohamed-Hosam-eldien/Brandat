package com.example.brandat.data.repos.products

import com.example.brandat.models.*
import retrofit2.Response

//@ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped

//@ActivityRetainedScoped
//@ViewModelScoped
interface IProductsRepository {

    suspend fun getProductDetails(productId:Long):Response<Product>

    suspend fun getCategories(productId: Long): Response<Products>
    suspend fun getbrand():Response<Brands>
}