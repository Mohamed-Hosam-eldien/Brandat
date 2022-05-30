package com.example.brandat.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.brandat.ui.fragments.address.AddressModel

@Database(
    entities = arrayOf(AddressModel::class),
    version = 1,
    exportSchema = false
)
//@TypeConverters(TypeConverter::class)
abstract class BrandatDataBase : RoomDatabase() {
    abstract fun brandatDao():BrandatDao

}