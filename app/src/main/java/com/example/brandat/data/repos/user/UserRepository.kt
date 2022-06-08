package com.example.brandat.data.repos.user

import android.content.ContentValues.TAG
import android.util.Log
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Customer
import com.example.brandat.models.CustomerAddress
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import javax.inject.Inject

//@ViewModelScoped
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
//=======================================================
    override suspend fun registerCustomer(customer: Customer): Response<Customer> {
    Log.e(TAG, "rpoooo:${remoteDataSource.registerCustomer(customer).body()} " )
    Log.e(TAG, "rpoooo:${remoteDataSource.registerCustomer(customer).errorBody()} "
    )
    Log.e(TAG, "rpoooo:${remoteDataSource.registerCustomer(customer).code()} " )



    return remoteDataSource.registerCustomer(customer)
    }


}