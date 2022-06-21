package com.example.brandat.ui.fragments.profile

import android.app.AlertDialog
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.FragmentProfileDataBinding
import com.example.brandat.models.Customer
import com.example.brandat.ui.MainActivity
import com.example.brandat.ui.fragments.cart.Cart
import com.example.brandat.ui.fragments.cart.CartViewModel
import com.example.brandat.ui.fragments.cart.IBadgeCount
import com.example.brandat.ui.fragments.favorite.FavouriteViewModel
import com.example.brandat.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class ProfileDataFragment : Fragment() {

    private lateinit var binding:FragmentProfileDataBinding
    private val cartViewModel: CartViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_profile_data, container, false)
        binding = FragmentProfileDataBinding.bind(view)

        Paper.init(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtName.text = Paper.book().read("name")
        binding.txtDefaultAddress.text = Paper.book().read("email")


        binding.orderLayout.setOnClickListener{
            // navigate to orders fragment
            findNavController().navigate(R.id.action_profileDataFragment_to_myOrderFragment)
        }
        binding.termsLayout.setOnClickListener{
            // navigate to language fragment
            it.findNavController().navigate(R.id.action_profileDataFragment_to_termsOfServiceFragment)
        }
        binding.addressLayout.setOnClickListener{
            // navigate to addresses fragment
            it.findNavController().navigate(R.id.action_profileDataFragment_to_addressFragment)
        }
        binding.currencyLayout.setOnClickListener{
            // navigate to currency fragment
            it.findNavController().navigate(R.id.action_profileDataFragment_to_currencyFragment)
        }

        binding.btnSignOut.setOnClickListener{
            showDialog()
        }

    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(context?.getString(R.string.yes)) { _, _ ->

            binding.btnSignOut.visibility = View.VISIBLE
            binding.progress.visibility = View.VISIBLE

            cartViewModel.getAllCartProduct()
            cartViewModel.cartProduct.observe(viewLifecycleOwner, Observer {
                if(it != null) {
                    cartViewModel.removeSelectedProductsFromCart(it)
                    Paper.book().delete("email")
                    Paper.book().delete("name")
                    Paper.book().delete("id")
                    Paper.book().delete("count")
                    Paper.book().destroy()
                    Constants.user = Customer()
                    requireActivity().finish()
                }
            })

        }
        builder.setNegativeButton(context?.getString(R.string.no)) { _, _ ->
        }
        builder.setTitle(context?.getString(R.string.are_you_sure))
        // builder.setMessage("Are you sure you want to delete ${product.pName.toLowerCase()} from Cart?")
        builder.create().show()
    }


}