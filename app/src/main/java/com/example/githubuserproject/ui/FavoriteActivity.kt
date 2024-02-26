package com.example.githubuserproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserproject.MainActivity
import com.example.githubuserproject.Result
import com.example.githubuserproject.adapter.FavoriteAdapter
import com.example.githubuserproject.databinding.ActivityFavoriteBinding
import com.example.githubuserproject.factory.ViewModelFactory
import com.example.githubuserproject.repositories.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private var _binding : ActivityFavoriteBinding? = null
    private val binding get() = _binding

    private val favoriteViewModel : FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupRecycleView()


        favoriteViewModel.getAllFavUser().observe(this){ result ->
            when(result){
                is Result.Loading -> {
                    binding?.loadingfav?.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding?.loadingfav?.visibility = View.GONE
                    val data =result.data
                    val adapter = FavoriteAdapter(data)
                    binding?.rvListuserFav?.adapter = adapter
                }
                is Result.Error ->{
                    binding?.loadingfav?.visibility = View.GONE
                    Toast.makeText(
                        this,
                        result.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        binding?.backbtnfav?.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

    }

    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        binding?.rvListuserFav?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvListuserFav?.addItemDecoration(itemDecoration)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}