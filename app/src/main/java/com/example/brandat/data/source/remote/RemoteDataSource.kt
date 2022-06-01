package com.example.brandat.data.source.remote

import com.example.brandat.models.Brand
import com.example.brandat.models.Brands
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource@Inject constructor(private val networkService: NetworkService) :IRemoteDataSource{

    override suspend fun getBrands(): Response<Brands> {
        return networkService.getBrands()
    }


}

