package com.example.githubuserproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserproject.R
import com.example.githubuserproject.adapter.ViewPagerAdapter
import com.example.githubuserproject.data.db.FavoriteUser
import com.example.githubuserproject.databinding.ActivityDetailBinding
import com.example.githubuserproject.factory.FavoriteViewModelFactory
import com.example.githubuserproject.ui.viewmodel.DetailViewModel
import com.example.githubuserproject.ui.viewmodel.FavoriteViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var detailVm : DetailViewModel
    private lateinit var favUser : FavoriteUser
    private lateinit var namaUser : String
    var jmlfollowers = 0
    var jmlfollowing = 0
    var isFavorite = false

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val KEY_NAME_USER = "key_name_user"
    }

    private val favoriteViewModel : FavoriteViewModel by viewModels {
        FavoriteViewModelFactory.getInstance(this)
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailVm = ViewModelProvider(this)[DetailViewModel::class.java]

        val name = intent.getStringExtra(KEY_NAME_USER)

        detailVm.getDetail(name!!)

        detailVm.loading.observe(this){
            setLoading(it)
        }

        detailVm.user.observe(this) {
            namaUser = it.login
            jmlfollowers = it.followers
            jmlfollowing = it.following

            binding.tvNamaUser.text = it.name

            binding.tvUsernameDetail.text = it.login
            Glide.with(this)
                .load(it.avatarUrl)
                .into(binding.img)
            binding.tvFollowers.text = resources.getString(R.string.jmlfollowers, it.followers)
            binding.tvFollowing.text = resources.getString(R.string.jmlfollowing, it.following)

            favUser = FavoriteUser(it.login, it.avatarUrl)

            favoriteViewModel.getFavoriteUserByUsername(favUser.username).observe(this) {
                isFavorite = if (it == null) {
                    binding.fab.setImageResource(R.drawable.fav_border)
                    false
                } else {
                    binding.fab.setImageResource(R.drawable.fav_full)
                    true
                }
            }
        }


        detailVm.errorToastMessage.observe(this) {
            it?.let {
                showToast(it)
            }
        }

        binding.backbtn.setOnClickListener {
            onBackPressed()
        }

        binding.fab.setOnClickListener {
            if (isFavorite == false) {
                favoriteViewModel.addFavorite(favUser)
                showToast(resources.getString(R.string.suksesfav,favUser.username ))
            } else {
                favoriteViewModel.removeFav(favUser.username)
                showToast(resources.getString(R.string.suksesunfav,favUser.username ))
            }
        }

        setupViewPager(name)

        binding.share.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_kata, namaUser, jmlfollowers, jmlfollowing))
                type = "text/plain"
            }

            val title: String = resources.getString(R.string.share_opsi)
            val chooser: Intent = Intent.createChooser(sendIntent, title)

            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            }
        }

    }

    private fun setLoading(isLoading : Boolean){
        with(binding){
            loadingAtas.visibility = if(isLoading) View.VISIBLE else View.GONE
            img.visibility = if(isLoading) View.GONE else View.VISIBLE
            tvUsernameDetail.visibility = if(isLoading) View.GONE else View.VISIBLE
            tvNamaUser.visibility = if(isLoading) View.GONE else View.VISIBLE
            tvFollowing.visibility = if(isLoading) View.GONE else View.VISIBLE
            tvFollowers.visibility = if(isLoading) View.GONE else View.VISIBLE
            fab.visibility = if(isLoading) View.GONE else View.VISIBLE
            share.visibility = if(isLoading) View.GONE else View.VISIBLE
        }

    }

    private fun setupViewPager(name : String) {
        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter
        viewPagerAdapter.username = name
        TabLayoutMediator(binding.tabs, binding.viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}