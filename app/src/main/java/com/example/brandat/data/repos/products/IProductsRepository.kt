package com.example.brandat.data.repos.products

import com.example.brandat.models.*
import com.example.brandat.models.draftOrder.DraftOrder
import com.example.brandat.models.draftOrder.DraftOrderModel
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.ResponseResult
import com.example.brandat.ui.fragments.orderDetails.OrderItemModel
import retrofit2.Response

interface IProductsRepository {

    suspend fun getProductDetails(productId: Long): NetworkResult<Product?>

    suspend fun getAllProduct(): NetworkResult<Products?>

    suspend fun getAllProductByType(type: String): NetworkResult<Products?>

    suspend fun getbrand(): NetworkResult<Brands?>

    //Cart
    suspend fun addProductToCart(cartProduct: Cart)
    suspend fun removeProductFromCart(product: Cart)
    suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>)
    suspend fun getAllCartProducts(): List<Cart>
    suspend fun updateOrder(product: Cart)
    suspend fun getAllPrice(): Double

    suspend fun insertFavouriteProduct(favourite: Favourite)
    suspend fun removeFavouriteProduct(productId: Long)
    suspend fun getFavouriteProducts():List<Favourite>
    suspend fun  searchInFavouriteProducts(productName:String):Favourite
    suspend fun getCategories(productId: Long): Response<Products>


    // post order
    suspend fun createOrder(orders: OrderModel): ResponseResult<OrderResponse>

    suspend fun postFavDraft(draftModel: DraftOrderModel) : Response<DraftOrder>

    //discount code
    suspend fun  getDiscountCodes():ResponseResult<PriceRules>

    suspend fun isAdded(productName: String): Cart
    suspend fun postFavDraft(draftModel: com.example.brandat.models.draft.OrderModel): Response<com.example.brandat.ordermodel.OrderResponse>
    suspend fun getAllOrders(email: String?): Response<Orders>

}