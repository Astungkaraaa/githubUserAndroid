package com.example.githubuserproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserproject.data.response.FollowUserItem
import com.example.githubuserproject.databinding.UseritemBinding

class FollowAdapter(val user : List<FollowUserItem>) :
    RecyclerView.Adapter<FollowAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowAdapter.MyViewHolder {
        val binding = UseritemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowAdapter.MyViewHolder, position: Int) {
        val currItem = user[position]
        Glide.with(holder.itemView)
            .load(currItem.avatar_url)
            .into(holder.binding.ivProfile)
        holder.binding.tvNama.text = currItem.login
    }

    override fun getItemCount(): Int {
        return user.size
    }

    class MyViewHolder(val binding: UseritemBinding) : RecyclerView.ViewHolder(binding.root)

}