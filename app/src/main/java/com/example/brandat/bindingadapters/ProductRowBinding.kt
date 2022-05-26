package com.example.brandat.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.brandat.ui.fragments.category.ProductModel

class ProductRowBinding {

    companion object {
        @BindingAdapter("onProductClicked")
        @JvmStatic
        fun onProductClicked(productCard:CardView,product:ProductModel){
            productCard.setOnClickListener {
                Log.e("TAG", "onProductClicked: ", )
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
        fun loadImgFromUrl(image: ImageView, url: String) {
            image.load(url)
           // crossFade(600)
        }
    }
}