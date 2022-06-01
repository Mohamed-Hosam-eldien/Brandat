package com.example.brandat.data.source.remote

import com.example.brandat.models.Brand
import com.example.brandat.models.Brands
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface NetworkService {
    @GET("smart_collections.json")
    @Headers("X-Shopify-Access-Token: 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")
   suspend fun getBrands():Response<Brands>


}