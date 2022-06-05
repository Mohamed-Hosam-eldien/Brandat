package com.example.brandat.data.repos.products

import android.util.Log
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.models.Brand
import com.example.brandat.models.Brands
import com.example.brandat.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.log


class ProductsRepository @Inject constructor(
    var localDataSource: ILocalDataSource,
     var remoteDataSource: IRemoteDataSource
    ) :IProductsRepository{

    override suspend fun getCategories(productId: Long): Response<Products> {
        return remoteDataSource.getCategories(productId)
    }

    override suspend fun getbrand():Response<Brands> {
        return remoteDataSource.getBrands()
    }

    override suspend fun getAllProduct(): Response<Products> {
        return remoteDataSource.getAllProductsById()
    }

    override suspend fun getAllProductByType(type: String): Response<Products> {
        return remoteDataSource.getAllProductsByProductType(type)
    }


}