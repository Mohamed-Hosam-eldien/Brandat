 package com.example.brandat.ui.fragments.category

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.R
import com.example.brandat.databinding.ProductItemBinding
import com.example.brandat.models.Favourite
import com.example.brandat.models.ProductDetails
import com.example.brandat.utils.FavouriteDiffUtil
import com.example.brandat.utils.ProductDiffUtil


 class ProductRvAdapter(var onClickedListener: OnClickedListener) : RecyclerView.Adapter<ProductRvAdapter.ProductViewHolder>() {

   // private var products = emptyList<Product>()
    private var product = emptyList<ProductDetails>()
     private var favorites = emptyList<Favourite>()

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

           for ( i in 0 .. favorites.size.minus(1)){
                      if(product[position].id == favorites[i].productId){
                          holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
                          holder.binding.ivFavorite.tag="favorite"
                      }
                  }
              holder.binding.ivFavorite.setOnClickListener {
                  if (holder.binding.ivFavorite.tag != "favorite") {
                      holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
                      holder.binding.ivFavorite.tag = "favorite"
                  } else {

                      holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_fill)
                      holder.binding.ivFavorite.tag = "unFavorite"
                      val bm = (holder.binding.ivProduct.getDrawable() as BitmapDrawable).bitmap
                      onClickedListener.onFavClicked(setFavDataFake(currentProduct, bm),holder.binding.ivFavorite
                      )
                  }

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