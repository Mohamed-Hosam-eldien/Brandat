package com.example.brandat.data.source.remote

import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.mymodel.MyProducts
import com.example.brandat.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path


interface NetworkService {

    @Headers("X-Shopify-Access-Token: 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")
    @GET("collections/{collection_id}/products.json")
    suspend fun getCategoryByTag(@Path("collection_id") collection_id: Long): Response<Products>

}