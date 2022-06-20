package com.example.brandat.data.source.remote

import com.example.brandat.models.*
import com.example.brandat.models.draft.CustomerOrder
import com.example.brandat.models.draftOrder.CustomerDraftOrder
import com.example.brandat.models.draftOrder.DraftOrderResponse
import com.example.brandat.ordermodel.Order
import com.example.brandat.ordermodel.OrderModel
import com.example.brandat.test.OrderDraft
import com.example.brandat.test.OrderResponse
import com.example.brandat.ui.fragments.orderDetails.OrderItemModel
import com.example.brandat.models.orderModel.OrderModel
import com.example.brandat.models.orderModel.OrderResponse
import com.example.brandat.models.draftOrder.DraftOrder
import com.example.brandat.models.draftOrder.DraftOrderModel
import com.example.brandat.models.orderModel.DiscountCodes
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
    suspend fun getAllOrders(email: String?):Response<com.example.brandat.ordermodel.Orders>

    suspend fun postFavDraft(draftModel: com.example.brandat.models.draft.OrderModel): Response<com.example.brandat.ordermodel.OrderResponse>
    suspend fun createOrder(orders: OrderModel):Response<OrderResponse>
    suspend fun postFavDraft(draftModel: DraftOrderModel): Response<DraftOrder>
    suspend fun  getDiscountCodes():Response<PriceRules>
}