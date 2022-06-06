package com.example.brandat.data.source.remote

import com.example.brandat.models.Product
import com.example.brandat.models.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

import com.example.brandat.models.Brand
import com.example.brandat.models.Brands

interface NetworkService {

    @Headers("X-Shopify-Access-Token: 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")
    @GET("collections/{collection_id}/products.json")
    suspend fun getCategoryByTag(@Path("collection_id") collection_id: Long): Response<Products>

    @GET("smart_collections.json")
    @Headers("X-Shopify-Access-Token: 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")
   suspend fun getBrands():Response<Brands>

    @Headers("X-Shopify-Access-Token: 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")
    @GET("products/{id}.json")
    suspend fun getProductDetails(@Path("id") productId: Long): Response<Product>

}