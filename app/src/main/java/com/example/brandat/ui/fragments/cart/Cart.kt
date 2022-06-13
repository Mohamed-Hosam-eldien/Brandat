package com.example.brandat.ui.fragments.cart

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.brandat.R

@Entity(tableName="cart_table")
data class Cart(var pName:String="", var pPrice:String="", var pImage:String="", var pImageFav:Bitmap?=null, var pQuantity:Int=1, @PrimaryKey var pId:Long=0L, var tPrice:Double=0.0)
