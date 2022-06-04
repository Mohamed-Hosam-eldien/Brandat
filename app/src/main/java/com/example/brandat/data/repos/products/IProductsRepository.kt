package com.example.brandat.data.repos.products

//@ActivityRetainedScoped
import com.example.brandat.models.Brands
import com.example.brandat.models.Product
import com.example.brandat.models.Products
import com.example.brandat.ui.fragments.cart.Cart
import retrofit2.Response

//@ActivityRetainedScoped
//@ViewModelScoped
interface IProductsRepository {

    suspend fun getProductDetails(productId: Long): Response<Product>
    suspend fun getCategories(productId: Long): Response<Products>
    suspend fun getbrand(): Response<Brands>

    //Cart
    suspend fun addProductToCart(cartProduct: Cart)
    suspend fun removeProductFromCart(product: Cart)
    suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>)
    suspend fun getAllCartProducts(): List<Cart>


}