package com.example.brandat.data.source.remote

import com.example.brandat.models.*
import com.example.brandat.models.draftOrder.DraftOrder
import com.example.brandat.models.draftOrder.DraftOrderModel
import retrofit2.Response

interface IRemoteDataSource {

    suspend fun getCategories(productId: Long): Response<Products>
    suspend fun getBrands(): Response<Brands>
    suspend fun getAllProductsById(): Response<Products>
    suspend fun getProductDetails(productId: Long): Response<Product>
    suspend fun getAllProductsByProductType(product_type: String): Response<Products>
    suspend fun registerCustomer(customer: CustomerRegisterModel): Response<CustomerRegisterModel>
    suspend fun loginCustomer(email:String, tags:String): Response<CustomerModel>

    suspend fun postFavDraft(draftModel: DraftOrderModel): Response<DraftOrder>
}