package com.example.brandat.ui.fragments.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.FragmentAddAddressBinding
import com.example.brandat.models.CustomerAddress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAddressFragment : Fragment() {

    private var _binding : FragmentAddAddressBinding? = null
    private val binding get() = _binding!!
    private val viewModel:AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_address, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cites = resources.getStringArray(R.array.city_list)
        val citesAdapter = ArrayAdapter(
            requireContext(),
            R.layout.city_dropdown_item, cites
        )
        binding.autoCompleteCityTextView.setAdapter(citesAdapter)


        binding.addAddressBtn.setOnClickListener {
            if (checkValidity()) {
                val country = binding.autoCompleteTextView.text.toString().trim()
                val street = binding.streerAddress.text.toString().trim()
                val city = binding.autoCompleteCityTextView.text.toString().trim()

                viewModel.insertAddress(CustomerAddress(false,street, city, country))
               findNavController().popBackStack()

            } else {
                Toast.makeText(context ,getString(R.string.There_is_an_empty_field) , Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun checkValidity(): Boolean {
        return when {
            binding.autoCompleteTextView.text.toString().trim().isNullOrEmpty() -> {
                binding.autoCompleteTextView.error =getString(R.string.required)
                false
            }
            binding.autoCompleteCityTextView.text.toString().trim().isNullOrEmpty() -> {
                binding.autoCompleteCityTextView.error = getString(R.string.required)
                false
            }
            binding.streerAddress.text.toString().trim().isNullOrEmpty() -> {
                binding.streerAddress.error =getString(R.string.required)
                false
            }
            else -> true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}