package com.example.brandat.ui.fragments.category

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.CategoryViewModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentProductBinding
import com.example.brandat.models.Favourite
import com.example.brandat.models.Product
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.favorite.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductFragment : Fragment(),OnClickedListener, IProduct {

    private lateinit var productRvAdapter: ProductRvAdapter
    private lateinit var binding: FragmentProductBinding
    private val products: ArrayList<ProductModel> = ArrayList()
    private var productID = 395728191717L

    //val model : SharedViewModel by viewModels()


    private val viewModel: CategoryViewModel by viewModels()
    private val favouriteViewModel: FavouriteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



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

        viewModel.categoryResponse.observe(requireActivity()) {
            for(product in it.body()?.products!!) {
                Log.d("TAG", "Product -->  ${product.title}")
                products.add(ProductModel(product.title, "98", product.imageProduct.src))
            }
            productRvAdapter.setData(it.body()!!.products)
        }

        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        model.positionMutable.observe(viewLifecycleOwner, Observer {
            when(it) {

                0 -> {
                    productID = 395728191717L
                }
                1 -> {
                    productID = 395728158949L
                }
                2 -> {
                    productID = 395728126181L
                }
                3 -> {
                    productID = 395728224485L
                }

            }

            viewModel.getCategory(productID)

        })

    }

    private fun setUpRecyclerView() {
        productRvAdapter = ProductRvAdapter(this,)

        binding.rvProducts.apply {
            val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
            //productRvAdapter.setData(products)
            adapter = productRvAdapter

        }
    }
    private fun observeOnFav(){
        favouriteViewModel.getFavouriteProducts()
        favouriteViewModel.getFavouriteProducts.observe(viewLifecycleOwner){
             if (it!= null){
                 productRvAdapter.setFavData(it)
             }
        }
    }
    fun showShimmerEffect() {
       // binding.rvProducts
    }

    fun hideShimmerEffect() {

    }



    override fun getPosition(position: Int, context: Context) {
        Toast.makeText(context, "pos ${position}" , Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(currentProductId: Long) {

        val bundle = Bundle()
        bundle.putLong("productId", currentProductId)
        Log.d("TAG", "onClicked: ray7 ${currentProductId}")
        findNavController().navigate(R.id.action_categoryFragment_to_productDetailsFragment, bundle)
    }

    override fun onFavClicked(favourite: Favourite, ivImage: ImageView) {
        favouriteViewModel.insertFavouriteProduct(favourite)


    }

    override fun checkFavourite(favourite: Favourite) {

     print("")
    }

}
