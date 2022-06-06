package com.example.brandat.data.source.remote

import com.example.brandat.models.Brands

import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Path

interface NetworkService {

    @Headers("X-Shopify-Access-Token: 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")
    @GET("collections/{collection_id}/products.json?")
    suspend fun getCategoryByTag(@Path("collection_id") collection_id: Long): Response<Products>

    @GET("smart_collections.json")
    @Headers("X-Shopify-Access-Token: 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")
   suspend fun getBrands():Response<Brands>

    @Headers("X-Shopify-Access-Token: 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")
    @GET("products.json")
    suspend fun getProductsById(): Response<Products>

    @Headers("X-Shopify-Access-Token: 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")
    @GET("products.json?product_type=")
    suspend fun getProductsBySubCategory(@Query("product_type")  product_type : String): Response<Products>

    @Headers("X-Shopify-Access-Token: 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")
    @GET("products/{id}.json")
    suspend fun getProductDetails(@Path("id") productId: Long): Response<Product>

}