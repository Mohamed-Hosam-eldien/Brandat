package com.example.brandat.data.repos.user

import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.*
import com.example.brandat.utils.NetworkResult
import javax.inject.Inject

class UserRepository @Inject constructor(
    private var localDataSource: ILocalDataSource,
    private var remoteDataSource: IRemoteDataSource
) : IUserRepository{

    override suspend fun insertAddress(customerAddress: CustomerAddress?) {
        localDataSource.insertAddress(customerAddress)

    }

    override suspend fun getAllAddresses(): List<CustomerAddress> {
        return localDataSource.getAllAddresses()
    }

    override suspend fun removeAddress(city: String) {
        localDataSource.removeAddress(city)
    }

    override suspend fun registerCustomer(customer: CustomerRegisterModel): NetworkResult<CustomerRegisterModel?> {
        val result: NetworkResult<CustomerRegisterModel?>
        val response=remoteDataSource.registerCustomer(customer)
        result = if(response.isSuccessful){
            NetworkResult.Success(response.body())
        }else{
            NetworkResult.Error("error${response.code()}")
        }
    return result
    }

    override suspend fun loginCustomer(email: String, tags:String): NetworkResult<CustomerModel?> {
       val result:NetworkResult<CustomerModel?>
       val response=remoteDataSource.loginCustomer(email,tags)
        result = if(response.isSuccessful){
            NetworkResult.Success(response.body())
        }else{
            NetworkResult.Error("error${response.code()}")
        }
        return result
    }


}