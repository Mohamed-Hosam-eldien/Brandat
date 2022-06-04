package com.example.brandat.data.repos.products

import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.Product
import retrofit2.Response

//@ActivityRetainedScoped
interface IProductsRepository {

    suspend fun getProductDetails(productId:Long):Response<Product>
    suspend fun insertAddress(customerAddress: CustomerAddress?)
}