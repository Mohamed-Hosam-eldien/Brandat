package com.example.brandat.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import coil.load
import com.example.brandat.R
import com.example.brandat.models.Product
import com.example.brandat.ui.fragments.category.ProductModel
import com.example.brandat.ui.fragments.favorite.FavouriteViewModel

class ProductRowBinding {

    companion object {
        @BindingAdapter("onProductClicked")
        @JvmStatic
        fun onProductClicked(productCard:CardView,product:Product){
            Log.e("TAG", "onProductClicked: ", )
            productCard.setOnClickListener {
//
//                val navController = Navigation.findNavController(productCard)
//                navController.navigate(R.id.action_productFragment_to_cartFragment)
            }
        }
//        @BindingAdapter("onFavClicked")
//        @JvmStatic
//        fun onFavClicked(favImag:ImageView,product:ProductModel , favouriteViewModel: FavouriteViewModel){
//            favImag.setOnClickListener {
//
//
//
//            }
//        }
//        @BindingAdapter("onCartClicked")
//        @JvmStatic
//        fun onCartClicked(cartImag:ImageView,product:Product){
//            cartImag.setOnClickListener {
//                Log.e("TAG", "onCartClicked: ", )
//            }
//        }


       // when dealing with API , use this method instead
        @BindingAdapter("setImgForProduct")
        @JvmStatic
        fun setImgForProduct(image: ImageView, url: Int) {
            image.load(url)
          //  crossFade(600)
        }
   }
}