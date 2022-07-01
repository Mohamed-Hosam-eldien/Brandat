package com.example.brandat.bindingadapters

import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.brandat.models.ProductDetails

class ProductRowBinding {

    companion object {
        @BindingAdapter("onProductClicked")
        @JvmStatic
        fun onProductClicked(productCard: CardView, product: ProductDetails) {
            productCard.setOnClickListener {

            }
        }

        @BindingAdapter("onFavClicked")
        @JvmStatic
        fun onFavClicked(favImag: ImageView, product: ProductDetails) {
            favImag.setOnClickListener {
            }
        }

        @BindingAdapter("onCartClicked")
        @JvmStatic
        fun onCartClicked(cartImag: ImageView, product: ProductDetails) {
            cartImag.setOnClickListener {

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
        }
    }
}