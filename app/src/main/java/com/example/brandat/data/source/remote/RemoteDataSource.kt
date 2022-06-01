package com.example.brandat.data.source.remote

import android.util.Log
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.mymodel.MyProducts
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val networkService: NetworkService
) :IRemoteDataSource {

    override suspend fun getCategories(productId: Long): Response<Products> {
        return networkService.getCategoryByTag(productId)
    }

}

