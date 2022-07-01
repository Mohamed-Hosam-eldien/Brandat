package com.example.brandat.data.repos.products

import com.example.brandat.utils.NetworkResult
import com.example.brandat.models.Brands
import com.example.brandat.models.Favourite
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.models.orderModel.AllOrderResponse
import com.example.brandat.models.orderModel.OrderModel
import com.example.brandat.models.orderModel.OrderResponse
import com.example.brandat.models.orderModel.discount.PriceRules
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.ResponseResult


interface IProductsRepository {

    suspend fun getProductDetails(productId:Long):NetworkResult<Product?>

    suspend fun getAllProduct(): NetworkResult<Products?>

    suspend fun getAllProductByType(type:String): NetworkResult<Products?>

    suspend fun getbrand(): NetworkResult<Brands?>

    //Cart
    suspend fun addProductToCart(cartProduct: Cart)
    suspend fun removeProductFromCart(product: Cart)
    suspend fun removeSelectedProductsFromCart(product: List<Cart>)
    suspend fun getAllCartProducts(): List<Cart>
    suspend fun updateOrder(product: Cart)
    suspend fun getAllPrice() : Double

    suspend fun insertFavouriteProduct(favourite: Favourite)
    suspend fun removeFavouriteProduct(productId: Long)
    suspend fun getFavouriteProducts():List<Favourite>
    suspend fun  searchInFavouriteProducts(productName:String):Favourite
    suspend fun getCategories(productId: Long): NetworkResult<Products?>


    // post order
    suspend fun createOrder(orders: OrderModel): ResponseResult<OrderResponse>

//    suspend fun postFavDraft(draftModel: DraftOrderModel) : Response<DraftOrder>

    //discount code
    suspend fun  getDiscountCodes():ResponseResult<PriceRules>

    suspend fun isAdded(productName: String): Cart
//    suspend fun postFavDraft(draftModel: com.example.brandat.models.draft.OrderModel): Response<com.example.brandat.ordermodel.OrderResponse>
    suspend fun getAllOrders(email: String?): NetworkResult<AllOrderResponse?>

}