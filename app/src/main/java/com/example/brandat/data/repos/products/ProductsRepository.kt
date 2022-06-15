package com.example.brandat.data.repos.products

import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.*
import com.example.brandat.models.draftOrder.DraftOrder
import com.example.brandat.models.draftOrder.DraftOrderModel
import com.example.brandat.ui.fragments.cart.Cart
import retrofit2.Response
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private var localDataSource: ILocalDataSource,
    private var remoteDataSource: IRemoteDataSource
    ) :IProductsRepository{

    override suspend fun insertFavouriteProduct(favourite: Favourite) {
        localDataSource.insertFavouriteProduct(favourite)
    }

    override suspend fun removeFavouriteProduct(productId: Long) {
        localDataSource.removeFavouriteProduct(productId)

    }

    override suspend fun getFavouriteProducts(): List<Favourite> {
        return localDataSource.getFavouriteProducts()
    }

    override suspend fun searchInFavouriteProducts(productName: String): Favourite {
        return localDataSource.searchInFavouriteProducts(productName)
    }

    override suspend fun getCategories(productId: Long): Response<Products> {
        return remoteDataSource.getCategories(productId)
    }

    override suspend fun postFavDraft(draftModel: DraftOrderModel): Response<DraftOrder> {
        return remoteDataSource.postFavDraft(draftModel)
    }

    override suspend fun getbrand():Response<Brands> {
        return remoteDataSource.getBrands()
    }
//=============================Cart=========================
    override suspend fun addProductToCart(cartProduct: Cart) {
        localDataSource.addProductToCart(cartProduct)
    }

    override suspend fun removeProductFromCart(product: Cart) {
        localDataSource.removeProductFromCart(product)
    }

    override suspend fun removeSelectedProductsFromCart(product: ArrayList<Cart>) {
        localDataSource.removeSelectedProductsFromCart(product)
    }

    override suspend fun getAllCartProducts(): List<Cart> {
       return localDataSource.getAllCartProducts()
    }

    override suspend fun updateOrder(product: Cart) {
        localDataSource.updateOrder(product)
    }

    override suspend fun getAllPrice(): Double {
        return localDataSource.getAllPrice()
    }

    override suspend fun getProductDetails(productId: Long): Response<Product> {
        return  remoteDataSource.getProductDetails(productId)
    }

    override suspend fun getAllProduct(): Response<Products> {
        return remoteDataSource.getAllProductsById()
    }

    override suspend fun getAllProductByType(type: String): Response<Products> {
        return remoteDataSource.getAllProductsByProductType(type)
    }


}