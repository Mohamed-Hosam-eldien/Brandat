package com.example.brandat.ui.fragments.category

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brandat.R
import com.example.brandat.databinding.ProductItemBinding
import com.example.brandat.models.Favourite
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.Constants
import com.example.brandat.utils.FavouriteDiffUtil
import com.example.brandat.utils.ProductDiffUtil
import com.example.brandat.utils.convertCurrency


class ProductRvAdapter(var context:Context,var onImageFavClickedListener: OnImageFavClickedListener) : RecyclerView.Adapter<ProductRvAdapter.ProductViewHolder>() {

   // private var products = emptyList<Product>()
    private var product = emptyList<ProductDetails>()
     private var favorites = emptyList<Favourite>()
    var currency :String = "USD"

    init {
        var  sharedPreferences =context.getSharedPreferences(Constants.SHARD_NAME,Context.MODE_PRIVATE)
       currency  = sharedPreferences.getString(Constants.CURRENCY_TYPE,"USD")!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        return ProductViewHolder(
            com.example.brandat.databinding.ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = product[position]
        holder.bind(currentProduct)
        Glide.with(holder.binding.root)
            .load(currentProduct.imageProduct.src)
            .into(holder.binding.ivProduct)
       holder.binding.tvProductPrice.text= convertCurrency(currentProduct.variants[currentProduct.variants.lastIndex].price.toDouble(),holder.itemView.context)
        holder.binding.productCurrency.text= currency
        for (i in 0..favorites.size.minus(1)) {
            if (currentProduct.title == favorites[i].productName) {
                holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
                holder.binding.ivFavorite.tag = "favorite"
            }
            else{

                holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_fill)
                holder.binding.ivFavorite.tag = "unFavorite"
            }

        }

        holder.binding.ivFavorite.setOnClickListener {
            if (holder.binding.ivFavorite.tag != "favorite") {
                holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
                holder.binding.ivFavorite.tag = "favorite"
                val bm = (holder.binding.ivProduct.getDrawable() as BitmapDrawable).bitmap
                onImageFavClickedListener.onFavClicked(
                    setFavDataFake(currentProduct, bm), holder.binding.ivFavorite)
            } else {
                  onImageFavClickedListener.deleteFavourite(currentProduct.id)
                holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_fill)
                holder.binding.ivFavorite.tag = "unFavorite"

            }

        }
        holder.binding.ivCart.setOnClickListener {
            onImageFavClickedListener.onCartClicked(setProductDataToCartModel(currentProduct))
        }

        holder.itemView.setOnClickListener {
            onImageFavClickedListener.onItemClicked(currentProduct.id)
        }
    }
    override fun getItemCount(): Int {
        return product.size
    }

    fun setData(newData: List<ProductDetails>) {
        val productDiffutil=ProductDiffUtil(product,newData)
        val productDiffUtilResult=DiffUtil.calculateDiff(productDiffutil)
        product = ArrayList(newData)
        productDiffUtilResult.dispatchUpdatesTo(this)

    }


      fun setFavData(newData: List<Favourite>) {
          val FavouriteDiffutil=FavouriteDiffUtil(favorites,newData)
          val productDiffUtilResult=DiffUtil.calculateDiff(FavouriteDiffutil)
          favorites = ArrayList(newData)
          productDiffUtilResult.dispatchUpdatesTo(this)

      }

    fun setProductDataToCartModel(productDetails: ProductDetails): Cart {
       // Log.d("TAG", "setProductDataToCartModel: ${productDetails.variants[0].price.toInt()}")
        return Cart(
            productDetails.title,
            productDetails.variants[0].price,
            pImage = productDetails.imageProduct.src,
            pId = productDetails.id,
            tPrice = productDetails.variants[0].price.toDouble(),
            variantId = productDetails.variants[0].id
        )
    }

    //============================================================
    class ProductViewHolder(val binding:ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productList: ProductDetails) {
            binding.product=productList
            binding.executePendingBindings()

        }

    }


    private fun setFavDataFake(product :ProductDetails,imageView: Bitmap):Favourite{
        return Favourite(
            productId = product.id,
            productImage = imageView,
            productName = product.title, productPrice = "88", isFav = 1
        )
    }

}








