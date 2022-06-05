package com.example.brandat.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.brandat.models.CustomerAddress

@Dao
 interface BrandatDao {

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertAddress(customerAddress: CustomerAddress)

 @Query("SELECT * FROM customeraddress")
 suspend fun getAllAddresses():List<CustomerAddress>

 @Query("DELETE FROM customeraddress WHERE city = :city")
 suspend fun removeAddress(city:String)


}