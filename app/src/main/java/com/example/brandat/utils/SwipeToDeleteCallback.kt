package com.example.brandat.utils

import android.R
import android.app.AlertDialog
import android.graphics.Canvas
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext


class SwipeToDeleteCallback: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false // We don't want support moving items up/down
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
//            val builder = AlertDialog.Builder(getContext())
//            builder.setTitle("Delete Task !")
//            builder.setMessage("Are you sure you want to delete this Task?")
//            builder.setPositiveButton(
//                "Yes"
//            ) { dialog, which ->
//
//            }
//            builder.setNegativeButton(
//                R.string.cancel
//            ) { dialog, which ->
//
//            }
//            val dialog = builder.create()
//            dialog.setCanceledOnTouchOutside(false)
//            dialog.show()
//
       }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
