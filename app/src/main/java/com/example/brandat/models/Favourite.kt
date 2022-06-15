package com.example.brandat.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable

@Entity
data class Favourite(
    @Nullable
    @PrimaryKey
    var productId:Long,
    var productName:String,
    var productPrice:String,
    val productImage:String,
    var isFav:Int

   ){
}
