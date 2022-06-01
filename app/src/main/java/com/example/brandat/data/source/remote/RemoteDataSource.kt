package com.example.brandat.data.source.remote

import android.util.Log
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import retrofit2.Response
import com.example.brandat.models.Brands
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val networkService: NetworkService
) :IRemoteDataSource {

    override suspend fun getBrands(): Response<Brands> {
        return networkService.getBrands()
    }

    override suspend fun getCategories(productId: Long): Response<Products> {
        return networkService.getCategoryByTag(productId)
    }

}

