package com.example.brandat.ui.fragments.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brandat.R
import com.example.brandat.databinding.FragmentAddressBinding
import com.example.brandat.models.CustomerAddress
import com.example.brandat.ui.fragments.productdetails.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify

@AndroidEntryPoint
class AddressFragment : Fragment() ,OnClickListener{
      lateinit var binding: FragmentAddressBinding

    private val addressAdapter by lazy { AddressAdapter(this) }
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


        showObservedData()

        binding.btnAddAddress.setOnClickListener {
            //  replace with code navigation to add address screen
            findNavController().navigate(R.id.action_addressFragment_to_addAddressFragment)
        }

        binding.btnOpenMao.setOnClickListener {
            //  replace with code navigation to map screen
            findNavController().navigate(R.id.action_addressFragment_to_mapsFragment)

        }
    }

    fun showObservedData(){
        viewModel.getAllAddress()
        viewModel.getAddresses.observe(viewLifecycleOwner){

            initView(it)
        }

    }

    private fun initView(addresses:List<CustomerAddress>) {
        addressAdapter.setDatat(addresses)
        //addressAdapter = AddressAdapter(this)
        binding.addressRecycler.apply {
           layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
           adapter=addressAdapter
       }

    }

    private fun fakeData(): ArrayList<CustomerAddress> {
      var addressList = ArrayList<CustomerAddress>()
        for (i in 1 .. 10) {
            addressList.add(CustomerAddress("ismalia ,  ", "egypt","mmm"))
        }
        return  addressList

    }

    companion object {

    }

    override fun onClick(customerAddress: CustomerAddress) {
        // remove address form recycler
        // dont forget to ask user berfor remove
        makeText(context, "deleted", Toast.LENGTH_SHORT).show()
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_,->
            viewModel.removeAddress(customerAddress.city)
            viewModel.getAllAddress()
            activity?.recreate()
        }
        builder.setNegativeButton("Cancel"){_,_,->

        }
        builder.setIcon(R.drawable.ic_delete)
        builder.setTitle("Delete This Address")
        builder.setMessage("Are you sure you want to delete ${customerAddress.address1} address")
        builder.create().show()

    }
}