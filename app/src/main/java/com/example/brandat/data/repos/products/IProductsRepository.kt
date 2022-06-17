package com.example.brandat.data.repos.products

import androidx.lifecycle.LiveData
import com.example.brandat.models.Brands
import com.example.brandat.models.Favourite
import com.example.brandat.models.OrderModel.OrderModel
import com.example.brandat.models.OrderModel.OrderResponse
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.ResponseError
import com.example.brandat.utils.ResponseResult
import retrofit2.Response

interface IProductsRepository {

    suspend fun getProductDetails(productId:Long):Response<Product>

    suspend fun getAllProduct(): Response<Products>

    suspend fun getAllProductByType(type:String): Response<Products>

    suspend fun getbrand(): Response<Brands>

    //Cart
    suspend fun addProductToCart(cartProduct: Cart)
    suspend fun removeProductFromCart(product: Cart)
    suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>)
    suspend fun getAllCartProducts(): List<Cart>
    suspend fun updateOrder(product: Cart)
    suspend fun getAllPrice() : Double
  // fav
    suspend fun insertFavouriteProduct(favourite: Favourite)
    suspend fun removeFavouriteProduct(productId: Long)
    suspend fun getFavouriteProducts():List<Favourite>
    suspend fun  searchInFavouriteProducts(productName:String):Favourite
    suspend fun getCategories(productId: Long): Response<Products>


    // post order
    suspend fun createOrder(orders: OrderModel): ResponseResult<OrderResponse>

}