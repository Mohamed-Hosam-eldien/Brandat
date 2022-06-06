package com.example.brandat.ui.fragments.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.FragmentFavoriteBinding
import com.example.brandat.models.Favourite
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList
@AndroidEntryPoint
class FavoriteFragment : Fragment(),OnclickListener {
    private lateinit var favouriteAdapter: FavouriteAdapter
    private lateinit var binding: FragmentFavoriteBinding
    private val favourite: List<Favourite> = ArrayList()
    private  val favouriteViewModel :FavouriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(LayoutInflater.from(context), container, false)
        setupUi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    private  fun observeShowData(){
        favouriteViewModel.getFavouriteProducts()
        favouriteViewModel.getFavouriteProducts.observe(viewLifecycleOwner) {
            it?.let{
                if (it.isEmpty()){
                    print(it.size)
                    binding.empty.visibility = View.VISIBLE

                }
                else{
                    binding.empty.visibility = View.GONE
                }
                favouriteAdapter.setData(it)
            }

        }

    }
    private fun setupUi() {
       favouriteAdapter = FavouriteAdapter(this)
        binding.rvFavorits.apply {
            val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            setLayoutManager(layoutManager)
            favouriteAdapter.setData(favourite)
            adapter = favouriteAdapter
            observeShowData()


        }
    }









    override fun onItemClicked(productId: Long) {

    }

    override fun onRemoveClicked(favourite: Favourite) {
          print("doooooooooooooo")
        val builder =AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_,->
            favouriteViewModel.removeFavouriteProduct(favourite.productId)
            showSnackbar()
        }
        builder.setNegativeButton("No"){_,_,->

        }
        builder.setTitle("Delete${favourite.productName.toLowerCase()}")
        builder.setMessage("Are you sure you want to delete ${favourite.productName} from favourite")
        builder.create().show()
    }

    private fun showSnackbar() {


    }


}

