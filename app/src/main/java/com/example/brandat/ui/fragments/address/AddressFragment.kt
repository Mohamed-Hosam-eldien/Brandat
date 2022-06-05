package com.example.brandat.ui.fragments.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brandat.R
import com.example.brandat.databinding.FragmentAddressBinding
import com.example.brandat.models.CustomerAddress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressFragment : Fragment() ,OnClickListener{
      lateinit var binding: FragmentAddressBinding
      lateinit var addressAdapter:AddressAdapter
      var addresses : List<CustomerAddress> = fakeData()
      private val viewModel:AddressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =DataBindingUtil.inflate(inflater,R.layout.fragment_address,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getAllAddress()
        viewModel.getAddresses.observe(viewLifecycleOwner){
            initView(it)
        }

        binding.btnAddAddress.setOnClickListener {
            //  replace with code navigation to add address screen
        }

        binding.btnOpenMao.setOnClickListener {
            //  replace with code navigation to map screen
            findNavController().navigate(R.id.action_addressFragment_to_mapsFragment)

        }
    }

    private fun initView(addresses:List<CustomerAddress>) {
        addressAdapter = AddressAdapter(addresses,this)
        binding.addressRecycler.apply {
           layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
           adapter=addressAdapter
       }

    }

    private fun fakeData(): ArrayList<CustomerAddress> {
      var addressList = ArrayList<CustomerAddress>()
        for (i in 1 .. 10) {
            addressList.add(CustomerAddress("iti,  ", "ismalia ,  ", "egypt"))
        }
        return  addressList

    }

    companion object {

    }

    override fun onClick() {
        // remove address form recycler
        // dont forget to ask user berfor remove
        makeText(context, "deleted", Toast.LENGTH_SHORT).show()

    }
}