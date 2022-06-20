package com.example.brandat.data.source.remote

import com.example.brandat.models.*
import com.example.brandat.models.orderModel.AllOrderResponse
import com.example.brandat.models.orderModel.discount.PriceRules
import retrofit2.Response

interface IRemoteDataSource {

    suspend fun getCategories(productId: Long): Response<Products>

    suspend fun getBrands(): Response<Brands>
    suspend fun getAllProductsById(): Response<Products>
    suspend fun getProductDetails(productId: Long): Response<Product>
    suspend fun getAllProductsByProductType(product_type: String): Response<Products>
    suspend fun registerCustomer(customer: CustomerRegisterModel): Response<CustomerRegisterModel>
    suspend fun loginCustomer(email:String, tags:String): Response<CustomerModel>
    suspend fun getAllOrders(email: String?):Response<AllOrderResponse>

//    suspend fun postFavDraft(draftModel: com.example.brandat.models.draft.OrderModel): Response<com.example.brandat.ordermodel.OrderResponse>
    suspend fun createOrder(orders: com.example.brandat.models.orderModel.OrderModel):Response<com.example.brandat.models.orderModel.OrderResponse>
//    suspend fun postFavDraft(draftModel: DraftOrderModel): Response<DraftOrder>
    suspend fun getDiscountCodes():Response<PriceRules>
}