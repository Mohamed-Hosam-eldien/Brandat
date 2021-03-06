package com.example.brandat.ui.fragments.productdetails

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
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
import com.example.brandat.ui.MainActivity
import com.example.brandat.ui.ProfileActivity
import com.example.brandat.ui.User
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.ui.fragments.cart.IBadgeCount
import com.example.brandat.ui.fragments.serach.SearchActivity
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants
import com.example.brandat.utils.convertCurrency
import com.example.brandat.utils.observeOnce
import com.example.brandat.viewmodels.ProductDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private var random: Float = 4.5F
    private lateinit var productToCart: Cart
    private lateinit var bageCountI: IBadgeCount
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var currency:String

    private var user: List<User> = listOf(
        User("Mohamed", "this product is so beautiful wwooooww", 3, R.drawable.man1),
        User("Ahmed", "I really liked this product so awesome", 4,R.drawable.man2),
        User("Galal", "Incredible product I bought it and I'll buy it again", 5, R.drawable.man3),
        User("Salem", "it's fine when you wanna a bag for you school", 4, R.drawable.man4),
        User("Noor", "I got one for my sister and she loves it so much", 3,R.drawable.man5),
        User("Mazen", "I did't like it at all so bad material", 2, R.drawable.man6)
    )


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
    ): View {
        // Inflate the layout for this fragment
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)

        sharedPreferences = context?.getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)!!

        currency = sharedPreferences.getString(Constants.CURRENCY_TYPE, "EGP")!!.toString()

        bageCountI = if(requireActivity() is MainActivity)
            requireActivity() as MainActivity
        else
            requireActivity() as SearchActivity

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

        setupRecyclerView()
        val numberOfElements = 3
        val randomElements = user.asSequence().shuffled().take(numberOfElements).toList()
        mAdapter.setDatat(randomElements)
        observeShowData()
        ConnectionUtil.registerConnectivityNetworkMonitor(requireContext(), viewModel, requireActivity())

    }


    private fun addCartToDraft(currentProduct: Cart) {
        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
            .child("cart")
            .child(currentProduct.pId.toString())
            .setValue(currentProduct)
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
        builder.setTitle(context?.getString(R.string.login_to_add_to_cart))
        // builder.setMessage("Are you sure you want to delete ${product.pName.toLowerCase()} from Cart?")
        builder.create().show()
    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun shareProduct() {
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "Mohamed Galal Elsheikh")
            this.type = "text/plain"
        }
        startActivity(shareIntent)
    }

    private fun observeShowData() {
        viewModel.getProduct.observeOnce(viewLifecycleOwner) {
            if (it != null) {
                showData(it)
            }
        }

    }

    private fun showData(body: Product?) {
        if (body != null) {

            binding.productNameTv.text = body.productDetails.title.lowercase()
            binding.productPriceTv.text = convertCurrency(body.productDetails.variants?.get(0)?.price?.toDouble(),
                requireContext()).plus("  ").plus(currency)

            binding.description.text = body.productDetails.bodyHtml
            binding.oneSize.text = body.productDetails.options[0].values[0]
            when (body.productDetails.options[1].values[0]) {
                "blue" -> binding.colorView.setBackgroundColor(Color.BLUE)
                "red" -> binding.colorView.setBackgroundColor(Color.RED)
                "black" -> binding.colorView.setBackgroundColor(Color.BLACK)
                "yellow" -> binding.colorView.setBackgroundColor(Color.YELLOW)
            }

            val imageList = ArrayList<SlideModel>()
            for(image in body.productDetails.imageProducts) {
                imageList.add(SlideModel(image.src))
            }

            binding.butAddToCart.setOnClickListener {
                if (Paper.book().read<String>("email") == null) {
                    showDialog()
                } else {

                    addCartToDraft(productToCart)

                    cartViewModel.addProductToCart(productToCart)

                    if (Paper.book().read<Int>("count") != null) {
                        Paper.book().write("count", Paper.book().read<Int>("count")!! + 1)
                    } else {
                        Paper.book().write("count", 1)
                    }

                    bageCountI.updateBadgeCount(Paper.book().read<Int>("count")!!)
                    Toast.makeText(requireContext(), context?.getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show()
                }
            }

            binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
            productToCart = Cart(
                pName = body.productDetails.title,
                variant_id = body.productDetails.variants?.get(0)?.id,
                pPrice = body.productDetails.variants?.get(0)?.price,
                pImage = body.productDetails.imageProducts[0].src,
                tPrice = body.productDetails.variants?.get(0)?.price?.toDouble(),
                pId = body.productDetails.id,
                pQuantity = binding.elegantNumberButtonQuantity.number.toInt()
            )

        }
    }
}