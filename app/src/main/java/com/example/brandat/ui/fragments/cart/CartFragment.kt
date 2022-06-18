package com.example.brandat.ui.fragments.cart

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.AlertDialogBinding
import com.example.brandat.databinding.FragmentCartBinding
import com.example.brandat.ui.OrderStatus
import com.example.brandat.ui.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class CartFragment : Fragment(), CartOnClickListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartRvAdapter
    private lateinit var builder: AlertDialog.Builder
    private lateinit var bindingDialog: AlertDialogBinding
    private lateinit var dialog: AlertDialog

    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(LayoutInflater.from(context), container, false)

        bindingDialog =
            AlertDialogBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        Paper.init(requireContext())

        binding.buyButn.setOnClickListener {
            if(Paper.book().read<String>("email") == null) {
                showDialog()
            } else {
                if(binding.ivPlaceholder.visibility == View.VISIBLE) {
                    Toast.makeText(requireContext(), "cart is empty", Toast.LENGTH_SHORT).show()
                } else {
                    startActivity(Intent(requireContext(), OrderStatus::class.java))
                }
            }
        }
        cartViewModel.getAllCartProduct()

        cartViewModel.cartProduct.observe(viewLifecycleOwner) {
            Log.e("Cart", "============${it.size} ")
            cartAdapter.setData(it)
            checkEmptyList(it)
            binding.tvConut.text = "(${it.size} item)"
            cartAdapter.notifyDataSetChanged()
        }
        bindingDialog.cancelBtn.setOnClickListener { dialog.dismiss() }
        bindingDialog.deleteBtn.setOnClickListener {
//            cartViewModel.removeProductFromCart(order)
//            cartViewModel.getAllCartproduct()
//            requireActivity().recreate()
        }

        cartViewModel.getAllPrice()

        cartViewModel.allPrice.observe(viewLifecycleOwner) {
            binding.tvTprice.text = "$it $"
        }
    }


    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Login Now") { _, _ ->
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }
        builder.setNegativeButton("cancel") { _, _ ->
        }
        builder.setTitle("please register or login to add item in cart")
        // builder.setMessage("Are you sure you want to delete ${product.pName.toLowerCase()} from Cart?")
        builder.create().show()
    }

    private fun setUpRecyclerView() {
        cartAdapter = CartRvAdapter(requireContext(), requireActivity(), this)
        binding.rvCart.apply {
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            //layoutManager.stackFromEnd = true
            // layoutManager.reverseLayout = true
            setLayoutManager(layoutManager)

            adapter = cartAdapter
        }
    }

    override fun onLongClicked(order: ArrayList<Cart>) {
        Log.d("TAG", "size before delete: ${order.size}")
        cartViewModel.removeSelectedProductsFromCart(order)
        cartViewModel.getAllCartProduct()
        Log.d("TAG", "size after delete: ${order.size}")

    }

    override fun onClicked(order: Cart) {
        cartViewModel.removeProductFromCart(order)
        cartViewModel.getAllCartProduct()
        requireActivity().recreate()
    }

    override fun onPluseMinusClicked(count: Int, pId: Long, price: String) {
        val priceChange = price.toDouble()
        val _price = (count * priceChange)
//        Log.d("TAG", "onPluseMinusClicked: count---> $_price")
        val currentOrder = Cart(pQuantity = count, pId = pId, tPrice = _price)
        cartViewModel.updateOrder(currentOrder)
        cartViewModel.getAllCartProduct()
        cartViewModel.getAllPrice()
        cartAdapter.notifyDataSetChanged()
    }

    private fun checkEmptyList(list: List<Cart>) {
        if (list.isEmpty()) {
            binding.ivPlaceholder.visibility = View.VISIBLE
            binding.rvCart.visibility = View.GONE
        } else {
            binding.ivPlaceholder.visibility = View.GONE
            binding.rvCart.visibility = View.VISIBLE

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // binding = null
        cartAdapter.clearContextualActionMode()
    }

    //===============================================
    private fun showAddAlertDialoge() {
        builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(bindingDialog.root)
        dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
    }

    private fun showDialoge(products: Cart) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            // cartViewModel.removeSelectedProductsFromCart(products)//list=========
            requireActivity().recreate()
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete?")
        // builder.setMessage("Are you sure you want to delete ${product.pName.toLowerCase()} from Cart?")
        builder.create().show()
    }

    override fun onResume() {
        super.onResume()
        cartViewModel.getAllCartProduct()
    }

}