package com.example.brandat.data.repo

import androidx.lifecycle.MutableLiveData
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.data.repos.products.ProductsRepository
import com.example.brandat.models.*
import com.example.brandat.models.orderModel.Order
import com.example.brandat.models.orderModel.Orders
import com.example.brandat.ui.fragments.cart.Cart
import retrofit2.Response

class FakeProductRepo : IProductsRepository {

    private val brandsItem = mutableListOf<Brand>(Brand(), Brand(), Brand())
    private val ordersItem = mutableListOf<Order>(Order(), Order(), Order())

    private val bBrandsItem = Brands(brandsItem)
    private val oOrdersItem = Orders(ordersItem)


    override suspend fun getbrand(): Response<Brands> {
        return Response.success(bBrandsItem)
    }

    override suspend fun getAllOrders(email: String?): Response<Orders> {
        return Response.success(oOrdersItem)
    }

    override suspend fun getProductDetails(productId: Long): Response<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProduct(): Response<Products> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProductByType(type: String): Response<Products> {
        TODO("Not yet implemented")
    }

    override suspend fun addProductToCart(cartProduct: Cart) {
        TODO("Not yet implemented")
    }

    override suspend fun removeProductFromCart(product: Cart) {
        TODO("Not yet implemented")
    }

    override suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCartProducts(): List<Cart> {
        TODO("Not yet implemented")
    }

    override suspend fun updateOrder(product: Cart) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPrice(): Double {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavouriteProduct(favourite: Favourite) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavouriteProduct(productId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavouriteProducts(): List<Favourite> {
        TODO("Not yet implemented")
    }

    override suspend fun searchInFavouriteProducts(productName: String): Favourite {
        TODO("Not yet implemented")
    }

    override suspend fun getCategories(productId: Long): Response<Products> {
        TODO("Not yet implemented")
    }


}