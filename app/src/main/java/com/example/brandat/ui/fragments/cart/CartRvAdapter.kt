package com.example.brandat.ui.fragments.cart

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brandat.R
import com.example.brandat.databinding.CartItemBinding
import com.example.brandat.utils.CartDiffUtil
import com.google.android.material.snackbar.Snackbar

class CartRvAdapter(
    var context: Context,
    private val requireActivity: FragmentActivity,
    var onLongClicked: setOnLongClicked
) :
    RecyclerView.Adapter<CartRvAdapter.CartViewHolder>(), ActionMode.Callback {
    private var carts: List<Cart> = ArrayList()

    private lateinit var rootView: View
    private lateinit var mActionMode: ActionMode
    private var myViewHolders = arrayListOf<CartViewHolder>()
    private var selectedOrders = arrayListOf<Cart>()
    private var multiSelection = false

    fun setData(newData: ArrayList<Cart>) {
        val cartDiffUtil = CartDiffUtil(carts, newData)
        val cartDiffUtilResult = DiffUtil.calculateDiff(cartDiffUtil)
        carts = newData
        cartDiffUtilResult.dispatchUpdatesTo(this)
    }

    class CartViewHolder(val binding:CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartList: Cart) {
            binding.cart = cartList
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            CartItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView
        val currentCart = carts[position]
        holder.bind(currentCart)
        Glide.with(context).load(currentCart.pImage).into(holder.binding.imgProduct)
        holder.itemView.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentCart)
            } else {
                Log.e("TAG", "onBindViewHolder: ")
            }
        }
        holder.itemView.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentCart)
                true
            }
            true
        }
    }

    override fun getItemCount() = carts.size


    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.order_contextual_menu, menu)
        mActionMode = mode!!

        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_item_order_menue) {
            Toast.makeText(requireActivity, "${selectedOrders.size}", Toast.LENGTH_SHORT).show()
            selectedOrders.forEach {
                onLongClicked.onClicked(it)
            }
            multiSelection = false
        }

        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeCardStyle(holder, R.color.chipTextColor, R.color.white)
        }
        multiSelection = false
        // selectedRecipes.clear()
        // applyStatusBarColor(R.color.statusBarColor)
    }
    private fun applyActionModeTitle() {
        when (selectedOrders.size) {
            0 -> {
                mActionMode.finish()
                multiSelection = false
            }
            1 -> {
                mActionMode.title = "${selectedOrders.size} item selected"
                //  mActionMode.title= R.font.m
            }
            else -> {
                mActionMode.title = "${selectedOrders.size} items selected"
            }
        }
    }

    private fun applySelection(holder: CartViewHolder, currentOrder: Cart) {
        if (selectedOrders.contains(currentOrder)) {
            selectedOrders.remove(currentOrder)
            changeCardStyle(holder, R.color.chipTextColor, R.color.white)
            applyActionModeTitle()
        } else {
            selectedOrders.add(currentOrder)
            changeCardStyle(holder, R.color.red, R.color.green)
            applyActionModeTitle()
        }
    }

    private fun changeCardStyle(holder: CartViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.constraint.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.binding.cardOrder.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

}