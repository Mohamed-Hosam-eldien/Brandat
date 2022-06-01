package com.example.brandat.data.source.remote

import com.example.brandat.models.Product
import com.example.brandat.models.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    // @Query("id") searchById:String
    @Headers("X-Shopify-Access-Token: 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")
//    @GET("products/{id}.json")
//    suspend fun getProductDetails(
//        @Query("id") searchById:String
//    ):Response<Product>

    //retrieve Single product
    @GET("products/{id}.json")
    suspend fun getProductDetails(@Path("id") productId: Long): Response<Product>

}