package com.example.brandat.ui.fragments.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brandat.R
import com.example.brandat.databinding.FragmentAddressBinding


class AddressFragment : Fragment() ,OnClickListener{
      lateinit var binding: FragmentAddressBinding
      lateinit var addressAdapter:AddressAdapter

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
        initView()

        binding.btnAddAddress.setOnClickListener {
            //  replace with code navigation to add address screen
            makeText(requireContext(), "hello d3d3", Toast.LENGTH_SHORT).show()
        }

        binding.btnOpenMao.setOnClickListener {
            //  replace with code navigation to map screen

            makeText(requireContext(), "hello d3d3", Toast.LENGTH_SHORT).show()

        }
    }

    private fun initView() {
        addressAdapter = AddressAdapter(fakeData(),this)
        binding.addressRecycler.apply {
           layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
           adapter=addressAdapter
       }

    }

    private fun fakeData(): ArrayList<AddressModel> {
      var addressList = ArrayList<AddressModel>()
        for (i in 1 .. 10) {
            addressList.add(AddressModel("iti,  ", "ismalia ,  ", "egypt"))
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