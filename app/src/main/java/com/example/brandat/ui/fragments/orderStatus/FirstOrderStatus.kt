package com.example.brandat.ui.fragments.orderStatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brandat.R
import com.example.brandat.databinding.FirstOrderStatusBinding
import com.example.brandat.models.CustomerAddress
import com.example.brandat.ui.fragments.address.AddressAdapter
import com.example.brandat.ui.fragments.address.AddressViewModel
import com.example.brandat.ui.fragments.address.OnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstOrderStatus : Fragment(), OnClickListener {

    private var _binding: FirstOrderStatusBinding? = null
    private val binding get() = _binding!!
    private val viewModel:AddressViewModel by viewModels()
    private val mAdapter by lazy { AddressAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.first_order_status, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showObservedData()

        binding.animationView.setOnClickListener {
            findNavController().navigate(R.id.action_firstOrderStatus_to_addAddressFragment2)
        }
    }

    fun showObservedData(){
        viewModel.getAllAddress()
        viewModel.getAddresses.observe(viewLifecycleOwner){

            initView(it)
        }

    }

    private fun initView(addresses:List<CustomerAddress>) {
        if (addresses.isNotEmpty()){
            binding.animationView.visibility = View.INVISIBLE
            mAdapter.setDatat(addresses)
            //addressAdapter = AddressAdapter(this)
            binding.recyclerviewAddress.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                adapter=mAdapter
        }

        }

    }

    override fun onClick(city: CustomerAddress) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}