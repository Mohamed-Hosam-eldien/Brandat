package com.example.brandat.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.brandat.models.*
import com.example.brandat.models.orderModel.AllOrderResponse
import com.example.brandat.models.orderModel.OrderModel
import com.example.brandat.models.orderModel.OrderResponse
import com.example.brandat.models.orderModel.discount.PriceRules
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val networkService: NetworkService
) : IRemoteDataSource {

    override suspend fun getProductDetails(productId: Long): Response<Product> {
        return networkService.getProductDetails(productId)
    }

    override suspend fun getBrands(): Response<Brands> {
        return networkService.getBrands()
    }

    override suspend fun getAllProductsById(): Response<Products> {
       return networkService.getProductsById()
    }

    override suspend fun getAllProductsByProductType(product_type: String): Response<Products> {
        return networkService.getProductsBySubCategory(product_type)
    }
    override suspend fun getCategories(productId: Long): Response<Products> {
        return networkService.getCategoryByTag(productId)
    }

    //=====================================================
    override suspend fun registerCustomer(customer: CustomerRegisterModel): Response<CustomerRegisterModel> {
       return networkService.register(customer)
    }

    override suspend fun loginCustomer(email:String, tags:String): Response<CustomerModel> {
        return networkService.login(email, tags)
    }

    override suspend fun getAllOrders(email: String?): Response<AllOrderResponse> {
        return networkService.getAllOrders(email)
    }


//    override suspend fun postFavDraft(draftModel: com.example.brandat.models.draft.OrderModel): Response<com.example.brandat.ordermodel.OrderResponse> {
//        Log.e("TAG", "postFavDraft: ssss --> ${networkService.draftFavorite(draftModel)} ")
//        return networkService.draftFavorite(draftModel)
//    }

    override suspend fun getDiscountCodes(): Response<PriceRules> {
        Log.e(TAG, "getDiscountCodes: ${networkService.getDiscountCodes().body()}")
      return networkService.getDiscountCodes()

    }

    override suspend fun createOrder(orders: OrderModel): Response<OrderResponse> {

    return networkService.createOrder(orders)
    }


}

