package com.example.brandat.data.source.local

import com.example.brandat.data.source.local.db.BrandatDao
import com.example.brandat.models.CustomerAddress
import javax.inject.Inject

class LocalDataSource @Inject constructor(
   private var brandatDao: BrandatDao
) :ILocalDataSource {

    override suspend fun insertAddress(customerAddress: CustomerAddress?) {
            customerAddress?.let {
                brandatDao.insertAddress(it)
            }
    }


}