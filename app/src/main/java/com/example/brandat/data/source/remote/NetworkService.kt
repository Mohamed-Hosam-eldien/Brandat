package com.example.brandat.data.source.remote

import com.example.brandat.models.*
import com.example.brandat.models.OrderModel.OrderModel
import com.example.brandat.models.OrderModel.OrderResponse
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {
    @POST("orders.json")
    @Headers("X-Shopify-Access-Token: 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",
        "Content-Type:application/json")
    suspend fun createOrder(@Body orders: OrderModel):Response<OrderResponse>
    @Headers("X-Shopify-Access-Token: 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")
    @GET("collections/{collection_id}/products.json")
    suspend fun getCategoryByTag(@Path("collection_id") collection_id: Long): Response<Products>

    @GET("smart_collections.json")
    @Headers("X-Shopify-Access-Token: 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")
    suspend fun getBrands(): Response<Brands>

    @Headers("X-Shopify-Access-Token: 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")
    @GET("products.json")
    suspend fun getProductsById(): Response<Products>

    @Headers("X-Shopify-Access-Token: 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")
    @GET("products.json?product_type=")
    suspend fun getProductsBySubCategory(@Query("product_type") product_type: String): Response<Products>

    @Headers("X-Shopify-Access-Token: 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")
    @GET("products/{id}.json")
    suspend fun getProductDetails(@Path("id") productId: Long): Response<Product>

    //==================================
    @Headers("X-Shopify-Access-Token: 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",
        "Content-Type: application/json")
    @POST("customers.json")
    suspend fun register(@Body customer: CustomerRegisterModel):Response<CustomerRegisterModel>

    @Headers("X-Shopify-Access-Token: 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")
    @GET("customers.json")
    suspend fun login(@Query("email") email: String,@Query("tags") tags: String):Response<CustomerModel>
}