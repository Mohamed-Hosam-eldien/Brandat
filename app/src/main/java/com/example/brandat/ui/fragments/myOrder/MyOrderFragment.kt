package com.example.brandat.ui.fragments.myOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brandat.R
import com.example.brandat.databinding.FragmentMyOrderBinding
import com.example.brandat.models.orderModel.Order
import com.example.brandat.utils.ConnectionUtil
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class MyOrderFragment : Fragment(), OnItemClickLinter {

    lateinit var binding: FragmentMyOrderBinding

    lateinit var navController: NavController
    private val viewModel: MyOrderViewModel by viewModels()
    private val myOrderAdapter by lazy { MyOrderAdapter(this, requireContext()) }
    var email: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_order, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        Paper.init(requireContext())
        email = Paper.book().read<String>("email")
        ConnectionUtil.email = email.toString()

        if (ConnectionUtil.isNetworkAvailable(requireContext())) {
            //shimmer / loading
            viewModel.getOrdersFromApi(email)
        } else {
            showMessage(requireContext().getString(R.string.no_connection))
            binding.animationView.visibility = View.VISIBLE
        }
        initRecycler()
        showObservedData()
        ConnectionUtil.registerConnectivityNetworkMonitor(
            requireContext(),
            viewModel,
            requireActivity()
        )
    }

    private fun initRecycler() {

        binding.myOrderRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            adapter = myOrderAdapter
        }
    }

    fun showObservedData() {
        viewModel.getOrder.observe(viewLifecycleOwner) {
            if (it != null)
                initView(it)
        }
    }

    private fun initView(order: List<Order>) {
        myOrderAdapter.setDatat(order)
        binding.myOrderRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = myOrderAdapter
        }
    }

    override fun onClick(orderItem: Order) {
        val direct:NavDirections=MyOrderFragmentDirections.actionMyOrderFragmentToOrderDetailsFragment2(orderItem)
        findNavController().navigate(direct)
    }

    private fun showMessage(it: String) {
        Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).setBackgroundTint(
                resources.getColor(
                    R.color.black2
                )
            )
            .setActionTextColor(resources.getColor(R.color.white)).setAction("Close") {
            }.show()
    }

}
