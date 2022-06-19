package com.example.brandat.ui.fragments.profile

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.FragmentProfileDataBinding
import com.example.brandat.ui.fragments.cart.Cart
import io.paperdb.Paper

class ProfileDataFragment : Fragment() {

    private lateinit var binding:FragmentProfileDataBinding

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
            Paper.book().delete("email")
            Paper.book().delete("name")
            Paper.book().destroy()
            requireActivity().finish()
        }
        builder.setNegativeButton(context?.getString(R.string.no)) { _, _ ->
        }
        builder.setTitle(context?.getString(R.string.are_you_sure))
        // builder.setMessage("Are you sure you want to delete ${product.pName.toLowerCase()} from Cart?")
        builder.create().show()
    }


}