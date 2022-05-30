package com.example.brandat.data.repos.category

import retrofit2.http.GET

interface CategoryApi {

    @GET("collections/395728191717/products")
    suspend fun getCategoryByTag()

}