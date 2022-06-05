package com.example.brandat.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.brandat.models.CustomerAddress
import androidx.room.*
import com.example.brandat.ui.fragments.cart.Cart

@Dao
interface BrandatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(product: Cart)

    @Delete
    suspend fun removeProductFromCart(product: Cart)

    @Delete
    suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>)

    @Query("SELECT * From cart_table ")//ORDER BY pName ASC
    suspend fun getAllCartProducts(): List<Cart>


    @Query("update cart_table set pQuantity =:conut,pPrice=:price where pId =:id")
    suspend fun updateOrder( conut:Int,price:Int,id:Long)
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertAddress(customerAddress: CustomerAddress)

 @Query("SELECT * FROM customeraddress")
 suspend fun getAllAddresses():List<CustomerAddress>

 @Query("DELETE FROM customeraddress WHERE city = :city")
 suspend fun removeAddress(city:String)


}