package com.example.brandat.ui.fragments.cart

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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
    lateinit var currencyCode:String
    lateinit var sharedPreferences : SharedPreferences
    private lateinit var bageCountI: IBadgeCount
    private var badgeCount: Int = 0
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
        sharedPreferences =requireActivity().getSharedPreferences(Constants.SHARD_NAME, Context.MODE_PRIVATE)
        currencyCode = sharedPreferences.getString(Constants.CURRENCY_TYPE, "EGP")!!

        Paper.init(requireContext())
        setUpRecyclerView()
        bageCountI = requireActivity() as MainActivity

        getAllCartFromDraft()
        cartViewModel.getAllCartProduct()
        cartViewModel.cartProduct.observe(viewLifecycleOwner) {
            if(it.isEmpty()) {
                binding.ivPlaceholder.visibility = View.VISIBLE
                binding.rvCart.visibility = View.GONE
                binding.tvTprice.text = "0.0"
                binding.cartsCurrency.text = currencyCode
                binding.tvConut.text = "0 item"
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
                        "cart is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Constants.getDiscount=false
                    val intent =Intent(requireContext(), OrderStatus::class.java)
                    intent.putExtra("total",binding.tvTprice.text.toString())
                    startActivity(intent)
                }
            }
        }

//        cartViewModel.getAllCartProduct()
//
//        cartViewModel.cartProduct.observe(viewLifecycleOwner) {
//            Log.e("Cart", "============${it.size} ")
//            cartAdapter.setData(it)
//            checkEmptyList(it)
//            binding.tvConut.text = "(${it.size} item)"
//            bageCountI.updateBadgeCount(it.size)
//            cartAdapter.notifyDataSetChanged()
//            count = it.size
//            Paper.book().write("countFromCart", count)
//        }
//        cartViewModel.allPrice.observe(viewLifecycleOwner) {
//            binding.tvTprice.text = "$it $"
//        }



    }

    private fun getAllCartFromDraft() {
        cartAdapter = CartRvAdapter(requireContext(), requireActivity(), this)
        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
            .child("cart")
            .addValueEventListener(object : ValueEventListener {
                val list = mutableListOf<Cart>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(data in snapshot.children) {
                        list.add(data.getValue(Cart::class.java)!!)
                    }
                    if(list.isNotEmpty()) {
                        binding.rvCart.apply {
                            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

                            setLayoutManager(layoutManager)
                            cartAdapter.setData(list)
                            adapter = cartAdapter

                            var total = 0.0

                            list.forEach{
                                total += it.tPrice!!
                            }

                            binding.tvTprice.text = convertCurrency(total,requireContext()).toString()
                            binding.cartsCurrency.text = currencyCode
                            binding.tvConut.text = list.size.toString() + " item(s)"
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
        builder.setTitle(getString(R.string.worning_msg))
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

        FirebaseDatabase.getInstance()
            .getReference(Constants.user.id.toString())
            .child("cart")
            .child(order.pId.toString()).removeValue()

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

    override fun onPluseMinusClicked(count: Int, currentCart: Cart) {
        val priceChange = currentCart.pPrice?.toDouble()
        val _price = (count * priceChange!!)
//        val currentOrder = Cart(pQuantity = count, pId = pId, tPrice = _price)

        currentCart.tPrice = _price
        currentCart.pQuantity = count

        FirebaseDatabase.getInstance().getReference(Constants.user.id.toString())
            .child("cart")
            .child(currentCart.pId.toString())
            .setValue(currentCart)

//        FirebaseDatabase.getInstance().getReference("123456")
//            .child("cart")
//            .child(pId.toString())
//            .child("tquantity").setValue(count)


        //getAllCartFromDraft()

        requireActivity().recreate()
        //cartViewModel.updateOrder(currentOrder)
        //cartViewModel.getAllCartProduct()
        //cartViewModel.getAllPrice()
//        cartAdapter.notifyDataSetChanged()
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
        if(Constants.user.id <= 0) {
            binding.ivPlaceholder.visibility = View.VISIBLE
            binding.rvCart.visibility = View.GONE
            binding.tvTprice.text = "0"
            binding.tvConut.text = "0 item"
        }
        cartViewModel.getAllCartProduct()
    }

}