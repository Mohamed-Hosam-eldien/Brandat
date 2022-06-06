package com.example.brandat.data.repos.products

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Brands
import com.example.brandat.models.Favourite
import com.example.brandat.models.Products
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.log

//@ActivityRetainedScoped
//@ViewModelScoped

class ProductsRepository @Inject constructor(
    private var localDataSource: ILocalDataSource,
    private var remoteDataSource: IRemoteDataSource
    ) :IProductsRepository{
    override suspend fun insertFavouriteProduct(favourite: Favourite) {
        localDataSource.insertFavouriteProduct(favourite)
    }

    override suspend fun removeFavouriteProduct(productName:String) {
        localDataSource.removeFavouriteProduct(productName)

    }

    override suspend fun getFavouriteProducts(): List<Favourite> {
        return localDataSource.getFavouriteProducts()
    }

    override suspend fun searchInFavouriteProducts(productName: String): Favourite {
        return localDataSource.searchInFavouriteProducts(productName)
    }

    private suspend fun getCategory(productId: Long): Response<Products> {
        return remoteDataSource.getCategories(productId)
    }



    override suspend fun getbrand():Response<Brands> {
        Log.e("TAG", "==============getbrand:============= ${remoteDataSource.getBrands().body()}", )
        return remoteDataSource.getBrands()
    }


}