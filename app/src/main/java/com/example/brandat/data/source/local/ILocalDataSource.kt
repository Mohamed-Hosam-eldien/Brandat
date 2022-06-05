package com.example.brandat.data.source.local

import com.example.brandat.models.CustomerAddress

interface ILocalDataSource {

     suspend fun insertAddress(customerAddress: CustomerAddress?)
     suspend fun getAllAddresses():List<CustomerAddress>
     suspend fun removeAddress(city:String)
}