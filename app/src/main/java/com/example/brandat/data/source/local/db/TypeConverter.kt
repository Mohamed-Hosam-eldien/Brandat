package com.example.brandat.data.source.local.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream


class TypeConverter {

 @TypeConverter
  fun fromBitMap(bitmap:Bitmap):ByteArray{
     val outputStream = ByteArrayOutputStream()
     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
     return outputStream.toByteArray()
  }
    @TypeConverter
    fun toBitMap(byteArray: ByteArray):Bitmap{
        return  BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }
}