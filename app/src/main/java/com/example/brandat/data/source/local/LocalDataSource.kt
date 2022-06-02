package com.example.brandat.data.source.local

import androidx.lifecycle.LiveData
import com.example.brandat.data.source.local.db.BrandatDao
import com.example.brandat.models.Favourite
import javax.inject.Inject

class LocalDataSource @Inject constructor(private var brandatDao:BrandatDao) :ILocalDataSource {
    override suspend fun insertFavouriteProduct(favourite: Favourite) {
        brandatDao.insertFavouriteProduct(favourite)
    }

    override suspend fun removeFavouriteProduct(productName:String) {
         brandatDao.removeFavouriteProduct(productName)
    }

    override suspend fun getFavouriteProducts():List<Favourite> {
          return brandatDao.getFavouriteProducts()
    }

    override suspend fun searchInFavouriteProducts(productName: String): Favourite {
       return  brandatDao.searchInFavouriteProducts(productName)
    }


}