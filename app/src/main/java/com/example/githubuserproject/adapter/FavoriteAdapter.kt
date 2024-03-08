package com.example.githubuserproject.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserproject.data.db.FavoriteUser
import com.example.githubuserproject.databinding.UseritemBinding
import com.example.githubuserproject.ui.DetailActivity

class FavoriteAdapter(val user: List<FavoriteUser>?) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UseritemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem = user?.get(position)
        Glide.with(holder.itemView)
            .load(currItem?.avatarUrl)
            .into(holder.binding.ivProfile)
        holder.binding.tvNama.text = currItem?.username

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.KEY_NAME_USER,
                user?.get(holder.adapterPosition)?.username
            )
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int {
        return user?.size ?: 0
    }

    class MyViewHolder(val binding: UseritemBinding) : RecyclerView.ViewHolder(binding.root)

}