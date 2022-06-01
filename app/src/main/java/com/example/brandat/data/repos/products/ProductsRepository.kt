package com.example.brandat.data.repos.products

import android.util.Log
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Brand
import com.example.brandat.models.Brands
import com.example.brandat.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import javax.inject.Inject

//@ActivityRetainedScoped
//@ViewModelScoped
class ProductsRepository @Inject constructor(
    var localDataSource: ILocalDataSource,
     var remoteDataSource: IRemoteDataSource
    ) :IProductsRepository{

    override suspend fun getbrand():Response<Brands> {
        Log.e("TAG", "==============getbrand:============= ${remoteDataSource.getBrands().body()}", )
        return remoteDataSource.getBrands()
    }


}