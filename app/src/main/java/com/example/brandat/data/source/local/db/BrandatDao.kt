package com.example.brandat.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.brandat.models.CustomerAddress

@Dao
 interface BrandatDao {

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertAddress(customerAddress: CustomerAddress)




}