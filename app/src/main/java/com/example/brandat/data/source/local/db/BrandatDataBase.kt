package com.example.brandat.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.brandat.models.CustomerAddress

import androidx.room.TypeConverters
import com.example.brandat.models.Favourite
import com.example.brandat.ui.fragments.address.AddressModel
import com.example.brandat.ui.fragments.cart.Cart

// don forget to use automigration
@Database(
    entities = arrayOf(Favourite::class,CustomerAddress::class,Cart::class),
    version = 10,


)
@TypeConverters(TypeConverter::class)
abstract class BrandatDataBase : RoomDatabase() {
      abstract fun brandatDao():BrandatDao

}