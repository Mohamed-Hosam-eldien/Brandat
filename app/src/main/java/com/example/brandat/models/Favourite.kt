package com.example.brandat.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable

@Entity
data class Favourite(
    @Nullable
    @PrimaryKey
    var productName:String,
    var productPrice:String,
    var productId:Long,
    val productImage:Bitmap,
    var isFav:Int

   ){
}
