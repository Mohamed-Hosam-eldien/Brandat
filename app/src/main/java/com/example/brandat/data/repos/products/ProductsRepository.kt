package com.example.brandat.data.repos.products

import android.util.Log
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.mymodel.MyProducts
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.log


class ProductsRepository @Inject constructor(
    var localDataSource: ILocalDataSource,
    var remoteDataSource: IRemoteDataSource
) : IProductsRepository {

    private suspend fun getCategory(productId: Long): Response<Products> {
        return remoteDataSource.getCategories(productId)
    }

    override suspend fun getCategories(productId: Long): Response<Products> {
        return getCategory(productId)
    }

}