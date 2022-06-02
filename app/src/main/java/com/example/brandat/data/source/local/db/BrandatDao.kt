package com.example.brandat.data.source.local.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.brandat.models.Favourite

@Dao
 interface   BrandatDao {

       @Insert(onConflict = OnConflictStrategy.REPLACE)
      suspend fun insertFavouriteProduct(favourite: Favourite)
      @Query("DELETE FROM Favourite WHERE productName = :productName")
      suspend fun removeFavouriteProduct(productName:String)
     @Query("SELECT * FROM Favourite ")
     suspend fun getFavouriteProducts():List<Favourite>
     @Query("SELECT * FROM Favourite WHERE productName = :productName ")
     suspend fun  searchInFavouriteProducts(productName:String): Favourite
//    @Query("SELECT EXISTS(SELECT * FROM favourite WHERE productName = :productName)")
//    suspend fun  searchInFavouriteProducts(productName:String) : Favourite
}