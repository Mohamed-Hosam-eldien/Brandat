package com.example.brandat.data.repos.user

import com.example.brandat.models.Customer
import com.example.brandat.models.CustomerAddress
import retrofit2.Response

interface IUserRepository {

    suspend fun insertAddress(customerAddress: CustomerAddress?)
    suspend fun getAllAddresses():List<CustomerAddress>
    suspend fun removeAddress(city:String)
    suspend fun registerCustomer(customer: Customer): Response<Customer>
}