package com.example.brandat.data.repos.products


import android.content.ContentValues.TAG
import android.util.Log
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.models.Brands
import com.example.brandat.models.Favourite
import com.example.brandat.models.orderModel.OrderModel
import com.example.brandat.models.orderModel.OrderResponse
import com.example.brandat.models.draftOrder.DraftOrder
import com.example.brandat.models.draftOrder.DraftOrderModel
import com.example.brandat.models.orderModel.discount.PriceRules
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.ResponseResult
import retrofit2.Response
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private var localDataSource: ILocalDataSource,
    private var remoteDataSource: IRemoteDataSource
    ) :IProductsRepository{

    override suspend fun insertFavouriteProduct(favourite: Favourite) {
        localDataSource.insertFavouriteProduct(favourite)
    }

    override suspend fun removeFavouriteProduct(productId: Long) {
        localDataSource.removeFavouriteProduct(productId)

    }

    override suspend fun getFavouriteProducts(): List<Favourite> {
        return localDataSource.getFavouriteProducts()
    }

    override suspend fun searchInFavouriteProducts(productName: String): Favourite {
        return localDataSource.searchInFavouriteProducts(productName)
    }

    override suspend fun getCategories(productId: Long): Response<Products> {
        return remoteDataSource.getCategories(productId)
    }

    override suspend fun postFavDraft(draftModel: DraftOrderModel): Response<DraftOrder> {
        return remoteDataSource.postFavDraft(draftModel)
    }

    override suspend fun getDiscountCodes(): ResponseResult<PriceRules> {
       return try {
           var result = remoteDataSource.getDiscountCodes()
            if (result.isSuccessful){
                if ( result.body() != null) {
                    ResponseResult.Success(result.body()!!)
                }
                else{
                    ResponseResult.Error(result.errorBody().toString())
                }

            }
            else{
               ResponseResult.Error(result.message())
           }

       }
       catch (t: Throwable) {
           ResponseResult.Error(t.message)
       }

    }


    override suspend fun getbrand():Response<Brands> {
        return remoteDataSource.getBrands()
    }
//=============================Cart=========================
    override suspend fun addProductToCart(cartProduct: Cart) {
        localDataSource.addProductToCart(cartProduct)
    }

    override suspend fun removeProductFromCart(product: Cart) {
        localDataSource.removeProductFromCart(product)
    }

    override suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>) {
        localDataSource.removeSelectedProductsFromCart(product)
    }

    override suspend fun getAllCartProducts(): List<Cart> {
       return localDataSource.getAllCartProducts()
    }

    override suspend fun updateOrder(product: Cart) {
        localDataSource.updateOrder(product)
    }

    override suspend fun getAllPrice(): Double {
        return localDataSource.getAllPrice()
    }

    override suspend fun getProductDetails(productId: Long): Response<Product> {
        return  remoteDataSource.getProductDetails(productId)
    }

    override suspend fun getAllProduct(): Response<Products> {
        return remoteDataSource.getAllProductsById()
    }

    override suspend fun getAllProductByType(type: String): Response<Products> {
        return remoteDataSource.getAllProductsByProductType(type)
    }

    override suspend fun createOrder(order: OrderModel): ResponseResult<OrderResponse> {
        return try {
            val res = remoteDataSource.createOrder(order)
            Log.e(TAG, "createOrder: .${res.errorBody()}" )
            Log.e(TAG, "createOrder: .${res.code()}" )
            if (res.isSuccessful) {
                val responseBody = res.body()
                if (responseBody != null) {
                    ResponseResult.Success(responseBody)
                } else {
                    ResponseResult.Error(res.errorBody().toString())
                }
            } else {
                ResponseResult.Error(res.message())
            }

        } catch (t: Throwable) {
            ResponseResult.Error(t.message)
        }
    }
    }


