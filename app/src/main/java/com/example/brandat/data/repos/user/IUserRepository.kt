package com.example.brandat.data.repos.user

import com.example.brandat.models.CustomerAddress

interface IUserRepository {

    suspend fun insertAddress(customerAddress: CustomerAddress?)
    suspend fun getAllAddresses():List<CustomerAddress>

}