package com.example.brandat

import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.*
import com.example.brandat.models.orderModel.Orders
import com.example.brandat.ui.fragments.cart.Cart
import retrofit2.Response

class FakeDataSource(private val brands:MutableList<Brand>? = mutableListOf<Brand>()) : IRemoteDataSource{

    override suspend fun getCategories(productId: Long): Response<Products> {
        TODO("Not yet implemented")
    }

    override suspend fun getBrands(): Response<Brands> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProductsById(): Response<Products> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductDetails(productId: Long): Response<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProductsByProductType(product_type: String): Response<Products> {
        TODO("Not yet implemented")
    }

    override suspend fun registerCustomer(customer: CustomerRegisterModel): Response<CustomerRegisterModel> {
        TODO("Not yet implemented")
    }

    override suspend fun loginCustomer(email: String, tags: String): Response<CustomerModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllOrders(email: String?): Response<Orders> {
        TODO("Not yet implemented")
    }


}