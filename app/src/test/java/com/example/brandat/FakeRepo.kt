package com.example.brandat

import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.data.repos.products.ProductsRepository
import com.example.brandat.models.Brands
import com.example.brandat.models.Favourite
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.models.orderModel.Orders
import com.example.brandat.ui.fragments.cart.Cart
import retrofit2.Response

class FakeRepo : IProductsRepository {

    override suspend fun getProductDetails(productId: Long): Response<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProduct(): Response<Products> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProductByType(type: String): Response<Products> {
        TODO("Not yet implemented")
    }

    override suspend fun getbrand(): Response<Brands> {
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

    override suspend fun getAllOrders(email: String?): Response<Orders> {
        TODO("Not yet implemented")
    }
}