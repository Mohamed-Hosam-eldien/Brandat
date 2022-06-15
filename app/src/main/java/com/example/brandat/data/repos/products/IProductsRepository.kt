package com.example.brandat.data.repos.products

import com.example.brandat.models.*
import com.example.brandat.models.draftOrder.DraftOrder
import com.example.brandat.models.draftOrder.DraftOrderModel
import com.example.brandat.ui.fragments.cart.Cart
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

    suspend fun insertFavouriteProduct(favourite: Favourite)
    suspend fun removeFavouriteProduct(productId: Long)
    suspend fun getFavouriteProducts():List<Favourite>
    suspend fun  searchInFavouriteProducts(productName:String):Favourite
    suspend fun getCategories(productId: Long): Response<Products>

    suspend fun postFavDraft(draftModel: DraftOrderModel) : Response<DraftOrder>


}