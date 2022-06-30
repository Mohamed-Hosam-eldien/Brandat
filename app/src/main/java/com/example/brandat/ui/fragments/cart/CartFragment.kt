package com.example.brandat.ui.fragments.cart

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.brandat.R
import com.example.brandat.databinding.AlertDialogBinding
import com.example.brandat.databinding.FragmentCartBinding
import com.example.brandat.ui.MainActivity
import com.example.brandat.ui.OrderStatus
import com.example.brandat.ui.ProfileActivity
import com.example.brandat.utils.Constants
import com.example.brandat.utils.convertCurrency
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper

@AndroidEntryPoint
class CartFragment : Fragment(), CartOnClickListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartRvAdapter
    private lateinit var builder: AlertDialog.Builder
    private lateinit var bindingDialog: AlertDialogBinding
    private lateinit var dialog: AlertDialog
    lateinit var currencyCode: String
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var bageCountI: IBadgeCount
    private var badgeCount: Int = 0
    private val cartViewModel: CartViewModel by viewModels()
    private val listener by lazy {
        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
            .child("cart")
    }

    var currency: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(LayoutInflater.from(context), container, false)


        sharedPreferences = context?.getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)!!

        currency = sharedPreferences?.getString(Constants.CURRENCY_TYPE,getString(R.string.egypt_currency))!!.toString()

        bindingDialog =
            AlertDialogBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences =
            requireActivity().getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)
        currencyCode = sharedPreferences.getString(Constants.CURRENCY_TYPE, context?.getString(R.string.egypt_currency))!!

        Paper.init(requireContext())
        setUpRecyclerView()
        bageCountI = requireActivity() as MainActivity

        getAllCartFromDraft()
        cartViewModel.getAllCartProduct()
        cartViewModel.cartProduct.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.ivPlaceholder.visibility = View.VISIBLE
                binding.rvCart.visibility = View.GONE
                binding.cartsCurrency.text = currencyCode
                binding.tvTprice.text = getString(R.string.zero)
                binding.tvConut.text = getString(R.string.zero_item)
            }
        }


        //cartViewModel.getAllCartProduct()

        //cartViewModel.getAllPrice()

        binding.buyButn.setOnClickListener {
            if (Paper.book().read<String>("email") == null) {
                showDialog()
            } else {
                if (binding.ivPlaceholder.visibility == View.VISIBLE) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.cart_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Constants.getDiscount = false
                    val intent = Intent(requireContext(), OrderStatus::class.java)
                    intent.putExtra("total", binding.tvTprice.text.toString())
                    startActivity(intent)
                }
            }
        }

    }

    private fun getAllCartFromDraft() {
        cartAdapter = CartRvAdapter(requireContext(), requireActivity(), this)
        listener
            .addValueEventListener(object : ValueEventListener {
                val list = mutableListOf<Cart>()

                @SuppressLint("SetTextI18n")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        list.add(data.getValue(Cart::class.java)!!)
                    }

                    if (list.isNotEmpty()) {
                        binding.rvCart.apply {
                            val layoutManager =
                                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

                            setLayoutManager(layoutManager)
                            cartAdapter.setData(list)
                            adapter = cartAdapter

                            var total = 0.0

                            list.forEach {
                                total += it.tPrice!!
                            }

                            binding.tvTprice.text = convertCurrency(total, requireContext())
                            binding.cartsCurrency.text = currency

                            when (list.size) {
                                0, 1 -> binding.tvConut.text =
                                    "(${list.size} ${getString(R.string.item)}"
                                else -> binding.tvConut.text =
                                    "(${list.size} ${getString(R.string.items_i)}"
                            }
                            binding.cartsCurrency.text = currencyCode
                            binding.rvCart.visibility = View.VISIBLE
                            binding.ivPlaceholder.visibility = View.GONE
                        }
                    } else {
                        binding.rvCart.visibility = View.GONE
                        binding.ivPlaceholder.visibility = View.VISIBLE
                    }

                }

                override fun onCancelled(error: DatabaseError) {}

            })

    }


    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.login_now)) { _, _ ->
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
        }
        builder.setTitle(getString(R.string.login_to_add_to_cart))
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

    override fun onClicked(order: Cart) {
        // showDialoge(order)

        listener.child(order.pId.toString()).removeValue()

        cartViewModel.removeProductFromCart(order)

        cartViewModel.getAllCartProduct()

        if (Paper.book().read<Int>("count") != null) {
            Paper.book().write("count", Paper.book().read<Int>("count")!! - 1)
        } else {
            Paper.book().write("count", 0)
        }
        bageCountI.updateBadgeCount(Paper.book().read<Int>("count")!!)

        requireActivity().recreate()
    }

    override fun onDeleteClicked(order: Cart) {
        showDialoge(order)
    }

    override fun onPluseMinusClicked(count: Int, currentCart: Cart) {
        val priceChange = currentCart.pPrice?.toDouble()
        val _price = (count * priceChange!!)
//        val currentOrder = Cart(pQuantity = count, pId = pId, tPrice = _price)

        currentCart.tPrice = _price
        currentCart.pQuantity = count

//        val price = mapOf("tprice" to _price)
//        listOf(price)

        cartViewModel.updateOrder(currentCart)

        listener.child(currentCart.pId.toString())
            .setValue(currentCart)

        cartAdapter.notifyDataSetChanged()
        requireActivity().recreate()
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
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            cartViewModel.removeProductFromCart(products)
            // cartViewModel.getAllCartProduct()
            requireActivity().recreate()
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ ->

        }
        builder.setTitle(getString(R.string.delete))
        builder.setMessage(getString(R.string.delete_cart))
        builder.create().show()
    }

    override fun onResume() {
        super.onResume()
        if (Constants.user.id <= 0) {
            binding.ivPlaceholder.visibility = View.VISIBLE
            binding.rvCart.visibility = View.GONE
            binding.tvTprice.text = getString(R.string.zero)
            binding.tvConut.text = getString(R.string.zero_item)
        }
        cartViewModel.getAllCartProduct()
    }

}