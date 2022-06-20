package com.example.brandat.data.source.local.db

import androidx.room.*
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.Favourite
import com.example.brandat.ui.fragments.cart.Cart

@Dao
interface BrandatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(product: Cart)

    @Delete
    suspend fun removeProductFromCart(product: Cart)

    @Delete
    suspend fun removeSelectedProductsFromCart(product: List<Cart>)

    @Query("SELECT * From cart_table ")//ORDER BY pName ASC
    suspend fun getAllCartProducts(): List<Cart>


    @Query("update cart_table set pQuantity =:conut,tPrice=:totalPrice where pId =:id")
    suspend fun updateOrder(conut: Int, id: Long,totalPrice:Double)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(customerAddress: CustomerAddress)

    @Query("SELECT * FROM customeraddress")
    suspend fun getAllAddresses(): List<CustomerAddress>

    @Query("DELETE FROM customeraddress WHERE city = :city")
    suspend fun removeAddress(city: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteProduct(favourite: Favourite)

    @Query("DELETE FROM Favourite WHERE productId = :productId")
    suspend fun removeFavouriteProduct(productId: Long)

    @Query("SELECT * FROM Favourite ")
    suspend fun getFavouriteProducts(): List<Favourite>

    @Query("SELECT SUM(tPrice) FROM cart_table")
    suspend fun getAllPrice(): Double

    @Query("SELECT * FROM Favourite WHERE productName = :productName ")
    suspend fun searchInFavouriteProducts(productName: String): Favourite
//    @Query("SELECT EXISTS(SELECT * FROM favourite WHERE productName = :productName)")
//    suspend fun  searchInFavouriteProducts(productName:String) : Favourite
}