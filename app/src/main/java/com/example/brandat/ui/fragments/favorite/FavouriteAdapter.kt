package com.example.brandat.ui.fragments.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brandat.R
import com.example.brandat.databinding.FavouriteItemBinding
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.Constants
import com.example.brandat.utils.FavouriteDiffUtil
import com.example.brandat.utils.convertCurrency
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavouriteAdapter(val context: Context,var onClickedListener: OnclickListener) :
    RecyclerView.Adapter<FavouriteAdapter.ProductViewHolder>() {

    private var fav_products = emptyList<ProductDetails>()
    var currency :String = "USD"

    init {
        val sharedPreferences = context.getSharedPreferences(Constants.SHARD_NAME,Context.MODE_PRIVATE)
        currency = sharedPreferences.getString(Constants.CURRENCY_TYPE,"USD")!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            FavouriteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = fav_products[position]


        holder.binding.tvProductPrice.text= convertCurrency(currentProduct.variants?.get(
            currentProduct.variants!!.lastIndex)?.price?.toDouble(),holder.itemView.context)

        holder.binding.productCurrency.text= currency

        //holder.binding.ivProductFav.setImageBitmap(currentProduct.productImage)
        Glide.with(context).load(currentProduct.imageProduct.src).into(holder.binding.ivProductFav)
        holder.binding.tvProductName.text = currentProduct.title.lowercase()
//        holder.binding.tvProductPrice.text = currentProduct.variants?.get(0)?.price

        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
            .child("cart")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild(currentProduct.id.toString())) {
                        holder.binding.ivCart.setImageResource(R.drawable.cart_done)
                        holder.binding.ivCart.tag = "done"
                        holder.binding.ivCart.setBackgroundResource(R.drawable.cart_shape_back_done)
                    } else {
                        holder.binding.ivCart.tag = "notDone"
                        holder.binding.ivCart.setImageResource(R.drawable.ic_add_to_cart)
                        holder.binding.ivCart.setBackgroundResource(R.drawable.cart_shape_back)
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })


        holder.itemView.setOnClickListener {
            onClickedListener.onItemClicked(currentProduct.id)
        }
        holder.binding.ivFavorite.setOnClickListener {
            onClickedListener.onRemoveClicked(currentProduct)
        }
        holder.binding.ivCart.setOnClickListener {
            onClickedListener.onCartClicked(setProductDataToCartModel(currentProduct), holder.binding.ivCart)
        }
    }

    override fun getItemCount(): Int {
        return fav_products.size
    }

//    fun setData(newData: List<Favourite>) {
//        val favouriteDiffUtil = FavouriteDiffUtil(fav_products, newData)
//        val favDiffUtilResult = DiffUtil.calculateDiff(favouriteDiffUtil)
//        fav_products = ArrayList(newData)
//        favDiffUtilResult.dispatchUpdatesTo(this)
//
//    }

    fun setData(newData: MutableList<ProductDetails>) {
        val favouriteDiffUtil = FavouriteDiffUtil(fav_products, newData)
        val favDiffUtilResult = DiffUtil.calculateDiff(favouriteDiffUtil)
        fav_products = ArrayList(newData)
        favDiffUtilResult.dispatchUpdatesTo(this)

    }

    private fun setProductDataToCartModel(favProduct: ProductDetails): Cart {
        // Log.d("TAG", "setProductDataToCartModel: ${productDetails.variants[0].price.toInt()}")
        return Cart(
            favProduct.title,
            variant_id = favProduct.variants?.get(0)?.id,
            favProduct.variants?.get(0)?.price,
            favProduct.imageProduct.src,
            pId = favProduct.id
        )
    }

    class ProductViewHolder(val binding: FavouriteItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}
