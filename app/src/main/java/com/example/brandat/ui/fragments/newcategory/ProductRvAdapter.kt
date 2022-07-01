package com.example.brandat.ui.fragments.newcategory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brandat.R
import com.example.brandat.databinding.ProductItemBinding
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.utils.Constants
import com.example.brandat.utils.ProductDiffUtil
import com.example.brandat.utils.convertCurrency
import com.google.firebase.database.*

class ProductRvAdapter(
    var onImageFavClickedListener: OnImageFavClickedListener,
    val context: Context
) :
    RecyclerView.Adapter<ProductRvAdapter.ProductViewHolder>() {

    private var product = emptyList<ProductDetails>()
    private val listener = FirebaseDatabase.getInstance().getReference(Constants.user.id.toString())
    private val favListener = listener.child("fav")
    private val cartListener = listener.child("cart")

    var currency: String = "EGP"

    init {
        val sharedPreferences =
            context.getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)
        currency = sharedPreferences.getString(Constants.CURRENCY_TYPE,context.getString(R.string.egypt_currency))!!
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        return ProductViewHolder(
            ProductItemBinding.inflate(
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
            .placeholder(com.denzcoskun.imageslider.R.drawable.loading)
            .into(holder.binding.ivProduct)


        holder.binding.tvProductPrice.text = (convertCurrency(
            currentProduct.variants?.get(currentProduct.variants!!.lastIndex)?.price?.toDouble(),
            holder.itemView.context))

        holder.binding.productCurrency.text = currency

//        checkFavExist(currentProduct.id, holder.binding.ivFavorite)


        favListener.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(currentProduct.id.toString())) {
                    holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
                    holder.binding.ivFavorite.tag = "favorite"
                } else {
                    holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_fill)
                    holder.binding.ivFavorite.tag = "unfavorite"
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })


        cartListener.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(currentProduct.id.toString())) {
                    holder.binding.ivCart.setImageResource(R.drawable.ic_baseline_done_green)
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

        holder.binding.ivFavorite.setOnClickListener {
            if (holder.binding.ivFavorite.tag != "favorite") {
                onImageFavClickedListener.onFavClicked(currentProduct, holder.binding.ivFavorite)
            } else {
                onImageFavClickedListener.deleteFavourite(currentProduct.id, holder.binding.ivFavorite)
            }
        }

//        for (i in 0..favorites.size.minus(1)) {
//            if (currentProduct.title == favorites[i].productName) {
//                holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
//                holder.binding.ivFavorite.tag = "favorite"
//            }
//            else{
//                holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_fill)
//                holder.binding.ivFavorite.tag = "unFavorite"
//            }
//
//        }

//        holder.binding.ivFavorite.setOnClickListener {
//            if (holder.binding.ivFavorite.tag != "favorite") {
//                holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
////                holder.binding.ivFavorite.tag = "favorite"
//                onImageFavClickedListener.onFavClicked(currentProduct, holder.binding.ivFavorite)
//            } else {
//                onImageFavClickedListener.deleteFavourite(currentProduct.id, holder.binding.ivFavorite)
//                holder.binding.ivFavorite.setImageResource(R.drawable.ic_favorite_fill)
////                holder.binding.ivFavorite.tag = "unFavorite"
//            }
//        }

        holder.binding.ivCart.setOnClickListener {
            onImageFavClickedListener.onCartClicked(
                setProductDataToCartModel(currentProduct),
                holder.binding.ivCart
            )
        }

        holder.itemView.setOnClickListener {
            onImageFavClickedListener.onItemClicked(currentProduct.id)
        }
    }


    override fun getItemCount(): Int {
        return product.size
    }

    fun setData(newData: List<ProductDetails>) {
        val productDiffutil = ProductDiffUtil(product, newData)
        val productDiffUtilResult = DiffUtil.calculateDiff(productDiffutil)
        product = ArrayList(newData)
        productDiffUtilResult.dispatchUpdatesTo(this)

    }


//      fun setFavData(newData: List<Favourite>) {
//          val FavouriteDiffutil=FavouriteDiffUtil(favorites,newData)
//          val productDiffUtilResult=DiffUtil.calculateDiff(FavouriteDiffutil)
//          favorites = ArrayList(newData)
//          productDiffUtilResult.dispatchUpdatesTo(this)
//      }

    fun setProductDataToCartModel(productDetails: ProductDetails): Cart {
        // Log.d("TAG", "setProductDataToCartModel: ${productDetails.variants[0].price.toInt()}")
//        if(productDetails.variants.isNotEmpty()) {
        productDetails.variants?.get(0)?.price.let {
            return Cart(
                productDetails.title,
                variant_id = productDetails.variants?.get(0)?.id,
                productDetails.variants?.get(0)?.price,
                pImage = productDetails.imageProduct.src,
                pId = productDetails.id,
                tPrice = productDetails.variants?.get(0)?.price?.toDouble()
            )
        }

    }

    //============================================================
    class ProductViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productList: ProductDetails) {
            binding.product = productList
            binding.executePendingBindings()

        }

    }

}