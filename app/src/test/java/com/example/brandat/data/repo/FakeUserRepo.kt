package com.example.brandat.data.repo

import androidx.lifecycle.LiveData
import com.example.brandat.data.repos.user.IUserRepository
import com.example.brandat.models.Brand
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.CustomerModel
import com.example.brandat.models.CustomerRegisterModel
import retrofit2.Response

class FakeUserRepo():IUserRepository {

    private val _addressesItem = mutableListOf<CustomerAddress>()
    val addressesItem : List<CustomerAddress> = _addressesItem

    override suspend fun insertAddress(customerAddress: CustomerAddress?) {
        _addressesItem.add(customerAddress!!)
    }

    override suspend fun getAllAddresses(): List<CustomerAddress> {
        return addressesItem
    }

    override suspend fun removeAddress(city: String) {
        _addressesItem.clear()
    }

    override suspend fun registerCustomer(customer: CustomerRegisterModel): Response<CustomerRegisterModel> {
        TODO("Not yet implemented")
    }

    override suspend fun loginCustomer(email: String, tags: String): Response<CustomerModel> {
        TODO("Not yet implemented")
    }
}