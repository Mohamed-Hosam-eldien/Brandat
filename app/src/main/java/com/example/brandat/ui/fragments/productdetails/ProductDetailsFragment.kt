package com.example.brandat.ui.fragments.productdetails

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentProductDetailsBinding
import com.example.brandat.models.Product
import com.example.brandat.ui.ProfileActivity
import com.example.brandat.ui.User
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.viewmodels.ProductDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper


@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private var random: Float = 4.5F
    private lateinit var mProduct: Product
    private lateinit var productToCart: Cart
    private var user: List<User> = listOf(
        User("Mohamed", "this product is so beautiful wwooooww", 3),
        User("Ahmed", "I really liked this product so awesome", 4),
        User("Galal", "Incredible product I bought it and I'll buy it again", 5),
        User("Salem", "it's fine when you wanna a bag for you school", 4),
        User("Noor", "I got one for my sister and she loves it so much", 3),
        User("Yasmeen", "I did't like it at all so bad material", 2)
    )

    var soso = "7782820643045"

    private val viewModel: ProductDetailsViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val mAdapter by lazy { UserAdapter() }
    var id: Long = 0

    private lateinit var _binding: FragmentProductDetailsBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = requireArguments().getLong("productId")
        ConnectionUtil.id = id
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)

        Paper.init(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ConnectionUtil.isNetworkAvailable(requireContext())) {
            //loading
            viewModel.getProductDetailsFromDatabase(id)
        } else {
            showMessage(requireContext().getString(R.string.no_connection))
        }
        random = (3..5).random().toFloat()
        binding.ratingBar.rating = random
        binding.shareBtn.setOnClickListener {
            shareProduct()
        }
        binding.backBtn.setOnClickListener {
            requireActivity().finish()
        }
        binding.favoriteBtn.setOnClickListener {

        }
        binding.butAddToCart.setOnClickListener {
            if (Paper.book().read<String>("email") == null) {
                showDialog()
            } else {
                cartViewModel.addProductToCart(productToCart)
                Toast.makeText(requireContext(), context?.getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show()
            }
        }
        setupRecyclerView()
        var usersList: User? = user.asSequence().shuffled().find { true }
        val numberOfElements = 3
        val randomElements = user.asSequence().shuffled().take(numberOfElements).toList()
        mAdapter.setDatat(randomElements)
        observeShowData()
        ConnectionUtil.registerConnectivityNetworkMonitor(requireContext(), viewModel, requireActivity())

    }

    private fun showMessage(it: String) {
        Snackbar.make(requireView(), it, Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
                resources.getColor(
                    R.color.black2
                )
            )
            .setActionTextColor(resources.getColor(R.color.white)).setAction(context?.getString(R.string.close)) {
            }.show()
    }


    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(context?.getString(R.string.login_now)) { _, _ ->
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }
        builder.setNegativeButton(context?.getString(R.string.cancel)) { _, _ ->
        }
        builder.setTitle(context?.getString(R.string.worning_msg))
        // builder.setMessage("Are you sure you want to delete ${product.pName.toLowerCase()} from Cart?")
        builder.create().show()
    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun shareProduct() {
        var shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "Mohamed Galal Elsheikh")
            this.type = "text/plain"
        }
        startActivity(shareIntent)
    }

    private fun observeShowData() {
        //7782820708581L
        viewModel.getProduct.observe(viewLifecycleOwner) {
            if (it != null) {
                showData(it)
            }
        }


    }

    private fun showData(body: Product?) {
        if (body != null) {

            binding.productNameTv.text = body.productDetails.title
            binding.productPriceTv.text = body.productDetails.variants[0].price
            binding.description.text = body.productDetails.bodyHtml
            binding.oneSize.text = body.productDetails.options[0].values[0]
            when (body.productDetails.options[1].values[0]) {
                "blue" -> binding.colorView.setBackgroundColor(Color.BLUE)
                "red" -> binding.colorView.setBackgroundColor(Color.RED)
                "black" -> binding.colorView.setBackgroundColor(Color.BLACK)
                "yellow" -> binding.colorView.setBackgroundColor(Color.YELLOW)
            }


            val imageList = ArrayList<SlideModel>()
            imageList.add(SlideModel(body.productDetails.imageProducts[0].src))
            imageList.add(SlideModel(body.productDetails.imageProducts[1].src))
            imageList.add(SlideModel(body.productDetails.imageProducts[2].src))
            binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
            productToCart = Cart(
                body.productDetails.title,
                body.productDetails.variants[0].price,
                body.productDetails.imageProducts[0].src
            )

        }


    }
}