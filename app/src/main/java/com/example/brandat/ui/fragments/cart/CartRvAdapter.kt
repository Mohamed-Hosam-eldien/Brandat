package com.example.brandat.ui.fragments.cart

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brandat.R
import com.example.brandat.databinding.CartItemBinding
import com.example.brandat.utils.CartDiffUtil


class CartRvAdapter(
    var context: Context,
    private val requireActivity: FragmentActivity,
    var onClickListener: CartOnClickListener
) :
    RecyclerView.Adapter<CartRvAdapter.CartViewHolder>(), ActionMode.Callback {
    private var carts: List<Cart> = ArrayList()

    private lateinit var rootView: View
    private lateinit var mActionMode: ActionMode
    private var myViewHolders = arrayListOf<CartViewHolder>()
    private var selectedOrders = arrayListOf<Cart>()
    private var multiSelection = false
    private var totalPrice = 0.0

    fun setData(newData: List<Cart>) {
        val cartDiffUtil = CartDiffUtil(carts, newData)
        val cartDiffUtilResult = DiffUtil.calculateDiff(cartDiffUtil)
        carts = newData
        cartDiffUtilResult.dispatchUpdatesTo(this)
    }

    class CartViewHolder(val binding: CartItemBinding) :
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

        totalPrice += currentCart.pPrice!!.toDouble()
        holder.binding.ivDelete.setOnClickListener {
            onClickListener.onDeleteClicked(currentCart)
        }
        holder.binding.numberButton.number = currentCart.pQuantity.toString()

//        holder.binding.tvProductPrice.text = (currentCart.pPrice.toDouble()
//                * holder.binding.numberButton.number.toInt()).toString()

        if (currentCart.tPrice == 0.0) {
            holder.binding.tvProductPrice.text = currentCart.pPrice
        } else {
            holder.binding.tvProductPrice.text = currentCart.tPrice.toString()
        }

        holder.binding.tvProductName.text = currentCart.pName.lowercase()

//        if (position == carts.size - 1)
//            Log.d("TAG", "onBindViewHolder: $totalPrice")

        holder.binding.numberButton.setOnValueChangeListener { _, _, newValue ->
            onClickListener.onPluseMinusClicked(newValue, currentCart)
        }

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
            }
            true
        }
    }

    fun convertStringToBitmap(string: String?): Bitmap? {
        val byteArray1: ByteArray = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            byteArray1, 0,
            byteArray1.size
        )
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
            showDialog(mode)
        }
        return true
    }

    private fun showDialog(mode: ActionMode?) {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton(context.getString(R.string.yes)) { _, _ ->
            selectedOrders.forEach {
                onClickListener.onClicked(it)
            }

            multiSelection = false
            selectedOrders.clear()
            mode?.finish()
        }
        builder.setNegativeButton(context.getString(R.string.no)) { _, _ ->

        }
        builder.setIcon(R.drawable.ic_delete)
        builder.setTitle(context.getString(R.string.delete))
        when(selectedOrders.size){
            1 -> {
                builder.setMessage(context.getString(R.string.delete_item_cart))
            }
            else -> {
                builder.setMessage(context.getString(R.string.delete_items_cart))
            }
        }
       // builder.setMessage(context.getString(R.string.worning))
        builder.create().show()
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeCardStyle(holder, R.color.chipTextColor, R.color.white)
        }
        multiSelection = false
        selectedOrders.clear()
    }

    private fun applyActionModeTitle() {
        when (selectedOrders.size) {
            0 -> {
                mActionMode.finish()
                multiSelection = false
            }
            1 -> {
                mActionMode.title =
                    " ${selectedOrders.size}${context.getString(R.string.item_selected)}"

                //  mActionMode.title= R.font.m
            }
            else -> {
                mActionMode.title =
                    "${selectedOrders.size}${context.getString(R.string.items_selected)}"
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
        Log.d("TAG", "applySelection adapter ${selectedOrders.size}: ")
    }

    private fun changeCardStyle(holder: CartViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.constraint.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.binding.cardOrder.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

}