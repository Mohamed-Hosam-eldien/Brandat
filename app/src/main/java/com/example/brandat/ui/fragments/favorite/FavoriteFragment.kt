package com.example.brandat.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.FragmentFavoriteBinding
import com.example.brandat.models.Product
import com.example.brandat.ui.fragments.category.OnClickedListener
import com.example.brandat.ui.fragments.category.ProductModel
import com.example.brandat.ui.fragments.category.ProductRvAdapter

class FavoriteFragment : Fragment(),OnClickedListener {
    private lateinit var productRvAdapter: ProductRvAdapter
    private lateinit var binding: FragmentFavoriteBinding
    private val products: ArrayList<ProductModel> = ArrayList()
    private  val favouriteViewModel :FavouriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        products.add(ProductModel("CLASSIC", "3 Eg", R.drawable.sh2))
//        products.add(ProductModel("Dress", "370 Eg", R.drawable.sh))
//        products.add(ProductModel("CLASSIC BACKPACK", "379 Eg", R.drawable.dress_kid))
//

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(LayoutInflater.from(context), container, false)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        productRvAdapter = ProductRvAdapter(this)

        binding.rvFavorits.apply {
            val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
            //productRvAdapter.setData(products)
            adapter = productRvAdapter

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onClicked(currentProduct: Product) {
    }

}