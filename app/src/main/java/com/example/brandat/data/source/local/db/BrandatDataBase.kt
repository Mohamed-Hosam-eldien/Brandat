package com.example.brandat.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.brandat.models.Favourite
import com.example.brandat.ui.fragments.address.AddressModel
// don forget to use automigration
@Database(
    entities = arrayOf(Favourite::class),
    version = 8,

    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class BrandatDataBase : RoomDatabase() {
      abstract fun brandatDao():BrandatDao

}