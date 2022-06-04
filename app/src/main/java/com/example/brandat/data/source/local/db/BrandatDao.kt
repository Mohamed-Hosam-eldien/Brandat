package com.example.brandat.data.source.local.db

import androidx.lifecycle.LiveData
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
}