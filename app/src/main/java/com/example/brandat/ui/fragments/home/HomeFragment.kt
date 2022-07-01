package com.example.brandat.ui.fragments.home

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.brandat.R
import com.example.brandat.databinding.FragmentHomeBinding
import com.example.brandat.models.Brand
import com.example.brandat.models.orderModel.discount.PriceRule
import com.example.brandat.utils.ConnectionUtil
import com.example.brandat.utils.Constants
import com.example.brandat.utils.Constants.Companion.showSnackBar
import com.example.brandat.utils.ResponseResult
import com.example.brandat.utils.observeOnce
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter


@AndroidEntryPoint
class HomeFragment : Fragment(), BrandOnClickListner {

    lateinit var binding: FragmentHomeBinding
    lateinit var brandAdapter: BrandAdapter
    private lateinit var brands: List<Brand>
    private lateinit var bundle: Bundle
    var snack: Snackbar? = null
    private val brandViewModel: BrandViewModel by viewModels()
    private lateinit var observer: Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, pressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initView()
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageSlider()
        showDiscountCopone()
        discountInit()
        if (ConnectionUtil.isNetworkAvailable(requireContext())) {
            showShimmerEffect()
            brandViewModel.getDiscountCode()
            Handler().postDelayed({
                brandViewModel.getBrands()
            }, 3000)
        } else {
            showSnackBar(getString(R.string.no_connection))
            hideShimmerEffect()
            binding.animationView.visibility = View.VISIBLE
        }
        brandViewModel.discountCodes.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseResult.Success -> {
                    Constants.discounCde = it.data.price_rules
                    binding.txtCode.text = it.data.price_rules[0].title
                }
                else -> {}
            }
        }
        observer = brandViewModel.brandResponse.observeOnce(requireActivity()) {
            if (it != null) {
                binding.animationView.visibility = View.GONE
                brands = it
                brandAdapter.setData(brands)
                hideShimmerEffect()
            } else {
                showShimmerEffect()
            }
        }
        ConnectionUtil.registerConnectivityNetworkMonitor(
            requireContext(),
            brandViewModel,
            requireActivity()
        )
    }

    private fun showDiscountCopone() {
        binding.imgCopy.setOnClickListener {
            val clipboardManager =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText(getString(R.string.text), binding.txtCode.text)
            clipboardManager.setPrimaryClip(clipData)
            cardAnimation()
            Toast.makeText(requireContext(), getString(R.string.text_copied), Toast.LENGTH_SHORT).show()
        }
    }

    private fun randomDiscountCode(priceRules: List<PriceRule>) {
        val randomElements = (priceRules.indices).shuffled().random()
        binding.txtCode.text = priceRules[randomElements].title
    }

    private fun discountInit() {
        binding.imgGift.setOnClickListener {
            Constants.discounCde?.let {
                randomDiscountCode(Constants.discounCde!!)
            }
            cardAnimation()
        }
    }

    private fun cardAnimation() {
        TransitionManager.beginDelayedTransition(binding.giftCard, AutoTransition())
        binding.layoutCode.visibility = if (binding.layoutCode.visibility == View.GONE) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun imageSlider() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.ads4, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.ads9, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.ads7, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.ads1, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.ads5, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.ads2,ScaleTypes.FIT))
        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.setImageList(imageList)
    }

    private fun initView() {
        brandAdapter = BrandAdapter(requireContext(), this)
        binding.brandsRecycler.apply {
            val layoutManager = GridLayoutManager(context, 2)
            setLayoutManager(layoutManager)

            val scaleAdapter = ScaleInAnimationAdapter(brandAdapter).apply {
                setDuration(825)
                setFirstOnly(false)
            }

            adapter = scaleAdapter
        }

    }

    private fun showShimmerEffect() {
        binding.shimmerRecycle.showShimmerAdapter()
        binding.shimmerRecycle.visibility = View.VISIBLE
        binding.brandsRecycler.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerRecycle.hideShimmerAdapter()
        binding.shimmerRecycle.visibility = View.GONE
        binding.brandsRecycler.visibility = View.VISIBLE
    }

    override fun onBrandClick(brandId: String) {
        bundle = Bundle()
        bundle.putString("brandId", brandId)
        findNavController().navigate(R.id.action_homeFragment_to_newCategoryFragment, bundle)
    }


}