package com.example.brandat.data.repos.products

import com.example.brandat.models.Brand
import com.example.brandat.models.Brands
import com.example.brandat.models.Products
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
//@ActivityRetainedScoped
//@ViewModelScoped
interface IProductsRepository {

    suspend fun getCategories(productId: Long): Response<Products>


suspend fun getbrand():Response<Brands>
}