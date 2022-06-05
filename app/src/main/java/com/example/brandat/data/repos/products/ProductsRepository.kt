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

//@ActivityRetainedScoped
//@ViewModelScoped

class ProductsRepository @Inject constructor(
    private var localDataSource: ILocalDataSource,
    private var remoteDataSource: IRemoteDataSource
) : IProductsRepository {
    override suspend fun getProductDetails(productId: Long):Response<Product> {
        Log.d("TAG", "getProductDetails: $productId")
        return remoteDataSource.getProductDetails(productId)
    }

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