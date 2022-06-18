package com.example.brandat.data.source.remote

import android.util.Log
import com.example.brandat.models.*
import com.example.brandat.models.draftOrder.DraftOrder
import com.example.brandat.models.draftOrder.DraftOrderModel
import com.example.brandat.models.orderModel.Order
import com.example.brandat.models.orderModel.Orders
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

    override suspend fun getAllOrders(email: String?): Response<Orders> {
        return networkService.getAllOrders(email)
    }

    override suspend fun postFavDraft(draftModel: DraftOrderModel): Response<DraftOrder> {
        Log.e("TAG", "postFavDraft: ssss --> ${networkService.draftFavorite(draftModel)} ")
        return networkService.draftFavorite(draftModel)
    }

}

