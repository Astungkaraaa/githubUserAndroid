package com.example.githubuserproject.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserproject.DetailActivity
import com.example.githubuserproject.data.response.User
import com.example.githubuserproject.databinding.UseritemBinding

class UserAdapter(val user : List<User>) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UseritemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem = user[position]
        Glide.with(holder.itemView)
            .load(currItem.avatarUrl)
            .into(holder.binding.ivProfile)
        holder.binding.tvNama.text = currItem.login

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.KEY_NAME_USER, user[holder.adapterPosition].login)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    class MyViewHolder(val binding: UseritemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}