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
import com.example.brandat.utils.ProductDiffUtil


 class ProductRvAdapter(var onClickedListener: OnClickedListener) : RecyclerView.Adapter<ProductRvAdapter.ProductViewHolder>() {

   // private var products = emptyList<Product>()
    private var product = emptyList<ProductModel>()

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
        //val currentProducts = products[position]
     //holder.bind(currentProducts)
        holder.bind(currentProduct)
        //val bm = (holder.binding.ivProduct.getDrawable() as BitmapDrawable).bitmap

      //  onClickedListener.checkFavourite(setFavDataFake(currentProduct,bm),holder.binding.ivProduct)
        holder.itemView.setOnClickListener {
            onClickedListener .onItemClicked(currentProduct)
        }

        holder.binding.ivFavorite.setOnClickListener {
            val bm = (holder.binding.ivProduct.getDrawable() as BitmapDrawable).bitmap
            onClickedListener.onFavClicked(setFavDataFake(currentProduct,bm),holder.binding.ivFavorite)
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

    fun setData(newData: ArrayList<ProductModel>) {
        val productDiffutil=ProductDiffUtil(product,newData)
        val productDiffUtilResult=DiffUtil.calculateDiff(productDiffutil)
        product = ArrayList(newData)
        productDiffUtilResult.dispatchUpdatesTo(this)

    }

    //============================================================
    class ProductViewHolder(val binding:ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productList: ProductModel) {
            binding.product=productList
            binding.executePendingBindings()

        }

    }


    private fun setFavDataFake(product :ProductModel,imageView: Bitmap):Favourite{
        return Favourite(
            productId = 1,
            productImage = imageView,
            productName = product.productName, productPrice = "88", isFav = 1
        )
    }



}