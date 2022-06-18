package com.example.brandat.data.repos.products

import android.util.Log
import com.example.brandat.utils.NetworkResult
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Brands
import com.example.brandat.models.Favourite
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.ui.fragments.cart.Cart
import retrofit2.Response
import javax.inject.Inject

//@ActivityRetainedScoped
//@ViewModelScoped

class ProductsRepository @Inject constructor(
    private var localDataSource: ILocalDataSource,
    private var remoteDataSource: IRemoteDataSource
) : IProductsRepository {

    override suspend fun getCategories(productId: Long): NetworkResult<Products?> {
        val result: NetworkResult<Products?>
        val response=remoteDataSource.getCategories(productId)
        if(response.isSuccessful){
            result=NetworkResult.Success(response.body())
        }else{
            result=NetworkResult.Error(Exception("error${response.code()}"))
        }
        return result
    }

    override suspend fun getAllProduct(): NetworkResult<Products?> {
        val result:NetworkResult<Products?>
        val response=remoteDataSource.getAllProductsById()
        if(response.isSuccessful){
            result=NetworkResult.Success(response.body())
        }else{
            result=NetworkResult.Error(java.lang.Exception("error${response.code()}"))
        }
        return result
    }

    override suspend fun getAllProductByType(type: String): NetworkResult<Products?> {
        val result:NetworkResult<Products?>
        val response=remoteDataSource.getAllProductsByProductType(type)
        if(response.isSuccessful){
            result=NetworkResult.Success(response.body())
        }else{
            result=NetworkResult.Error(Exception("error${response.code()}"))
        }
        return result
    }

    override suspend fun getbrand(): NetworkResult<Brands?> {
        val result: NetworkResult<Brands?>
        val response = remoteDataSource.getBrands()
        if (response.isSuccessful) {
            Log.e("TAG", "==from repo:success ", )
            result = NetworkResult.Success(response.body())
        } else {
            Log.e("TAG", "==from repo:error ", )
            result = NetworkResult.Error(Exception("error${response.code()}"))
        }
        return result
    }
    override suspend fun getProductDetails(productId: Long): NetworkResult<Product?> {
        val result:NetworkResult<Product?>
        val response=remoteDataSource.getProductDetails(productId)
        if(response.isSuccessful){
            result=NetworkResult.Success(response.body())
        }else{
            result=NetworkResult.Error(java.lang.Exception("error${response.code()}"))
        }
        return result
    }

    override suspend fun isAdded(productName: String): Cart {
        return localDataSource.isAdded(productName)
        println("result from repo=====${localDataSource.isAdded(productName)}")
    override suspend fun postFavDraft(draftModel: DraftOrderModel): Response<DraftOrder> {
        return remoteDataSource.postFavDraft(draftModel)
    }

    override suspend fun getbrand():Response<Brands> {
        return remoteDataSource.getBrands()
    }

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





}