package com.example.brandat.data.repos.products

import com.example.brandat.utils.NetworkResult
import com.example.brandat.models.Brands
import com.example.brandat.models.Favourite
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.models.draftOrder.DraftOrder
import com.example.brandat.models.draftOrder.DraftOrderModel
import com.example.brandat.models.orderModel.AllOrderResponse
import com.example.brandat.models.orderModel.Order
import com.example.brandat.models.orderModel.Orders
import com.example.brandat.ui.fragments.cart.Cart
import retrofit2.Response

interface IProductsRepository {

    suspend fun getProductDetails(productId:Long):NetworkResult<Product?>

    suspend fun getAllProduct(): NetworkResult<Products?>

    suspend fun getAllProductByType(type:String): NetworkResult<Products?>

    suspend fun getbrand(): NetworkResult<Brands?>

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
    suspend fun getCategories(productId: Long): NetworkResult<Products?>
    suspend fun isAdded(productName: String): Cart
    suspend fun postFavDraft(draftModel: DraftOrderModel) : Response<DraftOrder>
    suspend fun getAllOrders(email:String?): NetworkResult<AllOrderResponse?>

}