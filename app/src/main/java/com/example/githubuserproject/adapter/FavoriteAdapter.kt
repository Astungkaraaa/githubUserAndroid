package com.example.githubuserproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserproject.data.db.FavoriteUser
import com.example.githubuserproject.databinding.UseritemBinding

class FavoriteAdapter(val user : List<FavoriteUser>) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.MyViewHolder {
        val binding = UseritemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.MyViewHolder, position: Int) {
        val currItem = user[position]
        Glide.with(holder.itemView)
            .load(currItem.avatarUrl)
            .into(holder.binding.ivProfile)
        holder.binding.tvNama.text = currItem.username
    }

    override fun getItemCount(): Int {
        return user.size
    }

    class MyViewHolder(val binding: UseritemBinding) : RecyclerView.ViewHolder(binding.root)

}