package com.example.brandat.data.repos.user

import com.example.brandat.models.Customer
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.CustomerModel
import com.example.brandat.models.CustomerRegisterModel
import com.example.brandat.utils.NetworkResult
import retrofit2.Response

interface IUserRepository {

    suspend fun insertAddress(customerAddress: CustomerAddress?)
    suspend fun getAllAddresses():List<CustomerAddress>
    suspend fun removeAddress(city:String)
    suspend fun registerCustomer(customer: CustomerRegisterModel): NetworkResult<CustomerRegisterModel?>
    suspend fun loginCustomer(email: String, tags:String): NetworkResult<CustomerModel?>
}