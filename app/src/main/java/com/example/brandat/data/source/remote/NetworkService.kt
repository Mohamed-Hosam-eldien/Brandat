package com.example.brandat.data.source.remote

import com.example.brandat.models.Brands

import com.example.brandat.models.Product
import com.example.brandat.models.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Path

interface NetworkService {

    @Headers("X-Shopify-Access-Token: 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")
    @GET("collections/{collection_id}/products.json")
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