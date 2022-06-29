package com.example.brandat.ui.fragments.productdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.ReviewerRowLayoutBinding
import com.example.brandat.ui.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var users = emptyList<User>()

    class ViewHolder(private var binding:ReviewerRowLayoutBinding)
        : RecyclerView.ViewHolder(binding.root){

        val image = binding.userImageview

        fun bind(user : User) {
            binding.user = user
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent : ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                var binding = ReviewerRowLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = users[position]
        holder.image.setImageResource(currentUser.image)
        holder.bind(user = currentUser)
    }

    override fun getItemCount(): Int = users.size


    fun setDatat(myUsers:List<User>){
        users = myUsers
        notifyDataSetChanged()
    }

}