package com.example.recyclerviewswipedraganddrop.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewswipedraganddrop.databinding.UserItemBinding
import com.example.recyclerviewswipedraganddrop.models.User


class MyDiffUtilAdapter :
    ListAdapter<User, MyDiffUtilAdapter.Vh>(MyDiffUtil()) {

    inner class Vh(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User) {
            binding.name.text = user.name
            binding.password.text = user.password
        }
    }

    /**
     * This is Nested class
     */
    class MyDiffUtil : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val user = getItem(position)
        holder.onBind(user)
    }

}