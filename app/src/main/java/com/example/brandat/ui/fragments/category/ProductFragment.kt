package com.example.brandat.ui.fragments.category

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.R
import com.example.brandat.databinding.FragmentProductBinding
import com.example.brandat.models.Favourite
import com.example.brandat.models.Product
import com.example.brandat.ui.fragments.favorite.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class ProductFragment : Fragment(),OnClickedListener {

    private lateinit var productRvAdapter: ProductRvAdapter
    private lateinit var binding: FragmentProductBinding
    private  var isFav: Boolean= false
    private val products: ArrayList<ProductModel> = ArrayList()
    private val productViewModel : ProductViewModel by viewModels()
    private val favouriteViewModel:FavouriteViewModel by viewModels()
     
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        products.add(ProductModel("CLASSIC BACKPACK", "379 Eg", R.drawable.sh5,1))
        products.add(ProductModel(" BACKPACK", "3700 Eg", R.drawable.sh3,1))
        products.add(ProductModel("CLASSIC", "3 Eg", R.drawable.sh2,1))
        products.add(ProductModel("Dress", "370 Eg", R.drawable.sh,1))
        products.add(ProductModel("CLASSIC BACKPACK1", "379 Eg", R.drawable.dress_kid,1))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(LayoutInflater.from(context), container, false)
        setUpRecyclerView()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpRecyclerView() {
        productRvAdapter = ProductRvAdapter(this)

        binding.rvProducts.apply {
            val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
           productRvAdapter.setData(products)
            adapter = productRvAdapter

        }
    }

    fun showShimmerEffect() {
       // binding.rvProducts
    }

    fun hideShimmerEffect() {

    }


    override fun onItemClicked(currentProduct: ProductModel) {

        findNavController().navigate(R.id.action_categoryFragment_to_productDetailsFragment)

    }

    override fun onFavClicked(favourite: Favourite,ivFav: ImageView) {
             showFavourite(favourite,ivFav)

    }

    override fun checkFavourite(favourite: Favourite,ivFav: ImageView) {
        showFavourite(favourite,ivFav)

    }

    private fun setStoredButton(ivFav: ImageView, productId:Int) {
        if (productId == 1) {
            ivFav.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            ivFav.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun searchForFav(productName:String){
        favouriteViewModel.searchForFavouriteInDatabase(productName)
        favouriteViewModel.getFavouriteProduct.observe(viewLifecycleOwner){
            if (it!=null){
                 isFav = true
            }
            else{
                isFav=false
            }
        }
        

    }

    private  fun  showFavourite(favourite: Favourite,ivFav: ImageView){

        searchForFav(favourite.productName)

        // not found
        if (isFav == false){
            Log.e(TAG, "onFavClicked: false", )
            favouriteViewModel.insertFavouriteProduct(favourite)
            setStoredButton(ivFav,1)

        }
        else {
            //found
            Log.e(TAG, "onFavClicked: true", )
            favouriteViewModel.removeFavouriteProduct(favourite.productName)
            setStoredButton(ivFav,0)

        }
      }

}
