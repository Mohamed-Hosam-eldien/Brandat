package com.example.brandat.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brandat.databinding.ReviewerRowLayoutBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var users = emptyList<User>()

    class ViewHolder(private var binding:ReviewerRowLayoutBinding)
        : RecyclerView.ViewHolder(binding.root){


        fun bind(user : User) {
            binding.user = user
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent : ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                var binding = ReviewerRowLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        var currentUser = users[position]
        holder.bind(user = currentUser)
    }

    override fun getItemCount(): Int = users.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDatat(myUsers:List<User>){
        users = myUsers
        notifyDataSetChanged()
    }

}