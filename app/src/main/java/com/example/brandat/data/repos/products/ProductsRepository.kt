package com.example.brandat.data.repos.products

import android.util.Log
import com.example.brandat.utils.NetworkResult
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Brands
import com.example.brandat.models.Favourite
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.models.orderModel.*
import com.example.brandat.models.orderModel.discount.PriceRules
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.ResponseResult
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private var localDataSource: ILocalDataSource,
    private var remoteDataSource: IRemoteDataSource
) : IProductsRepository {

    override suspend fun getCategories(productId: Long): NetworkResult<Products?> {
        val result: NetworkResult<Products?>
        val response=remoteDataSource.getCategories(productId)
        result = if(response.isSuccessful){
            NetworkResult.Success(response.body())
        }else{
            NetworkResult.Error("error${response.code()}")
        }
        return result
    }

    override suspend fun getAllProduct(): NetworkResult<Products?> {
        val result:NetworkResult<Products?>
        val response=remoteDataSource.getAllProductsById()
        result = if(response.isSuccessful){
            NetworkResult.Success(response.body())
        }else{
            NetworkResult.Error("error${response.code()}")
        }
        return result
    }

    override suspend fun getAllProductByType(type: String): NetworkResult<Products?> {
        val result:NetworkResult<Products?>
        val response=remoteDataSource.getAllProductsByProductType(type)
        result = if(response.isSuccessful){
            NetworkResult.Success(response.body())
        }else{
            NetworkResult.Error("error${response.code()}")
        }
        return result
    }

    override suspend fun getbrand(): NetworkResult<Brands?> {
        val result: NetworkResult<Brands?>
        val response = remoteDataSource.getBrands()
        result = if (response.isSuccessful) {
            NetworkResult.Success(response.body())
        } else {
            NetworkResult.Error("error${response.code()}")
        }
        return result
    }
    override suspend fun getProductDetails(productId: Long): NetworkResult<Product?> {
        val result:NetworkResult<Product?>
        val response=remoteDataSource.getProductDetails(productId)
        result = if(response.isSuccessful){
            NetworkResult.Success(response.body())
        }else{
            NetworkResult.Error("error${response.code()}")
        }
        return result
    }

    override suspend fun isAdded(productName: String): Cart {
        return localDataSource.isAdded(productName)
    }

    override suspend fun getAllOrders(email:String?): NetworkResult<AllOrderResponse?> {
        val result:NetworkResult<AllOrderResponse?>
        val response=remoteDataSource.getAllOrders(email)
        result = if(response.isSuccessful){
            NetworkResult.Success(response.body())
        }else{
            NetworkResult.Error("error${response.code()}")
        }
        return result
    }


    override suspend fun getDiscountCodes(): ResponseResult<PriceRules> {
       return try {
           val result = remoteDataSource.getDiscountCodes()
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

//=============================Cart=========================
    override suspend fun addProductToCart(cartProduct: Cart) {
        localDataSource.addProductToCart(cartProduct)
    }

    override suspend fun removeProductFromCart(product: Cart) {
        localDataSource.removeProductFromCart(product)
    }

    override suspend fun removeSelectedProductsFromCart(product: List<Cart>) {
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

    override suspend fun createOrder(order: OrderModel): ResponseResult<OrderResponse> {
        return try {
            val res = remoteDataSource.createOrder(order)
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


