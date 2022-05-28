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
import com.example.brandat.ui.fragments.category.ProductModel

class ProductRowBinding {

    companion object {
        @BindingAdapter("onProductClicked")
        @JvmStatic
        fun onProductClicked(productCard:CardView,product:ProductModel){
            Log.e("TAG", "onProductClicked: ", )
            productCard.setOnClickListener {

//                val navController = Navigation.findNavController(productCard)
//                navController.navigate(R.id.action_productFragment_to_cartFragment)
            }
        }
        @BindingAdapter("onFavClicked")
        @JvmStatic
        fun onFavClicked(favImag:ImageView,product:ProductModel){
            favImag.setOnClickListener {
                Log.e("TAG", "onFavClicked: ", )
            }
        }
        @BindingAdapter("onCartClicked")
        @JvmStatic
        fun onCartClicked(cartImag:ImageView,product:ProductModel){
            cartImag.setOnClickListener {
                Log.e("TAG", "onCartClicked: ", )
            }
        }

        @BindingAdapter("setProductImg")
        @JvmStatic
        fun setProductImg(imageView: ImageView, img: Int) {
            imageView.setImageResource(img).toString()
        }

        //when dealing with API , use this method instead
        @BindingAdapter("setImgForProduct")
        @JvmStatic
        fun setImgForProduct(image: ImageView, url: String) {
            image.load(url)
           // crossFade(600)
        }
    }
}