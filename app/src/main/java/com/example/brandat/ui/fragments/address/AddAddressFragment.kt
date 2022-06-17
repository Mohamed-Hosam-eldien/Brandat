package com.example.brandat.ui.fragments.address

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.FragmentAddAddressBinding
import com.example.brandat.models.CustomerAddress
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAddressFragment : Fragment() {

    private var _binding : FragmentAddAddressBinding? = null
    private val binding get() = _binding!!
    private val viewModel:AddressViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_address, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countries = resources.getStringArray(R.array.country_list)
        var countriesAdapter = ArrayAdapter(
            requireContext(),
            R.layout.city_dropdown_item, countries
        )
        binding.autoCompleteTextView.setAdapter(countriesAdapter)

        val cites = resources.getStringArray(R.array.city_list)
        var citesAdapter = ArrayAdapter(
            requireContext(),
            R.layout.city_dropdown_item, cites
        )
        binding.autoCompleteCityTextView.setAdapter(citesAdapter)


        binding.addAddressBtn.setOnClickListener {
            if (checkValidity()) {
                val country = binding.autoCompleteTextView.text.toString().trim()
                val street = binding.streerAddress.text.toString().trim()
                val city = binding.autoCompleteCityTextView.text.toString().trim()

                viewModel.insertAddress(CustomerAddress(street, city, country))
               findNavController().popBackStack()

            } else {
                Snackbar.make(
                    binding.root,
                    "ALL Fields end with * Reqiured please fill its .",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }

    }

    private fun checkValidity(): Boolean {
        if (binding.autoCompleteTextView.text.toString().trim().isNullOrEmpty()) {
            binding.autoCompleteTextView.error = "Field Required!"
            return false
        } else if (binding.autoCompleteCityTextView.text.toString().trim().isNullOrEmpty()) {
            binding.autoCompleteCityTextView.error = "Field Required!"
            return false
        } else if (binding.streerAddress.text.toString().trim().isNullOrEmpty()) {
            binding.streerAddress.error = "Field Required!"
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}