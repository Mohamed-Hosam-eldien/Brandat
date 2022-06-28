package com.example.brandat.ui.fragments.orderStatus

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brandat.R
import com.example.brandat.databinding.FirstOrderStatusBinding
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.orderModel.ShippingAddress
import com.example.brandat.ui.OrderStatus
import com.example.brandat.ui.fragments.address.AddressViewModel
import com.example.brandat.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstOrderStatus : Fragment(), OnRadioClickListener {

    private var _binding: FirstOrderStatusBinding? = null
    private val binding get() = _binding!!
    lateinit var iChangeOrderStatus: IChangeOrderStatus
    private val viewModel: AddressViewModel by viewModels()

    var selectedAddress: CustomerAddress? = null
    private val mAdapter by lazy { AddressPaymentAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.first_order_status, container, false)

        iChangeOrderStatus = requireActivity() as OrderStatus

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showObservedData()

        binding.mapBtn.setOnClickListener {
            findNavController().navigate(R.id.action_firstOrderStatus_to_mapsFragment2)
        }
        binding.addAddressBtn.setOnClickListener {
            findNavController().navigate(R.id.action_firstOrderStatus_to_addAddressFragment2)
        }

        binding.animationView.setOnClickListener {
            findNavController().navigate(R.id.action_firstOrderStatus_to_addAddressFragment2)
        }

        binding.btnNext.setOnClickListener {

            var bundle = Bundle()
            if (selectedAddress != null) {
                bundle.putParcelable("address", selectedAddress)
                Constants.getDiscount = false
                findNavController().navigate(
                    R.id.action_firstOrderStatus_to_secondOrderStatus,
                    bundle
                )
                iChangeOrderStatus.changeStatus(1)
            } else {
                Toast.makeText(context, "please select Address", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showObservedData() {
        viewModel.getAllAddress()
        viewModel.getAddresses.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                initView(it)
                selectedAddress = it[0]
            }
        }

    }

    private fun initView(addresses: List<CustomerAddress>) {
        if (addresses.isNotEmpty()) {
            binding.animationView.visibility = View.GONE
            binding.txt.visibility = View.GONE
            binding.recyclerviewAddress.visibility = View.VISIBLE
            binding.fabMenu.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
            mAdapter.setDatat(addresses)
            //addressAdapter = AddressAdapter(this)
            binding.recyclerviewAddress.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = mAdapter
            }

        }

    }

    override fun onItemClick(address: CustomerAddress) {
        Toast.makeText(context, "${address.address1} ..selected", Toast.LENGTH_SHORT).show()
        selectedAddress = address
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}