package com.example.brandat.data.source.remote

import android.util.Log
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource@Inject constructor(
    private val networkService: NetworkService
) :IRemoteDataSource{
    override suspend fun getProductDetails(productId: Long): Response<Product> {
        Log.d("TAG", "getProductDetails: ${networkService.getProductDetails(productId).body()}")
        Log.d("TAG", "getProductDetails id: $productId")

        return networkService.getProductDetails(productId)
    }

}

