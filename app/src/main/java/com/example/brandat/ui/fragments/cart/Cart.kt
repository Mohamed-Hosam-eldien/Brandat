package com.example.brandat.ui.fragments.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="cart_table")
data class Cart(var pName:String="",var pPrice:String="", var pImage:String="", var pQuantity:Int=1,@PrimaryKey var pId:Long=0L,var tPrice:Double=0.0,var variantId:Long)
