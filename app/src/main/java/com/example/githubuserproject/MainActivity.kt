package com.example.githubuserproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserproject.adapter.UserAdapter
import com.example.githubuserproject.data.response.User
import com.example.githubuserproject.databinding.ActivityMainBinding
import com.example.githubuserproject.datastore.SettingPreferences
import com.example.githubuserproject.datastore.dataStore
import com.example.githubuserproject.factory.ThemeViewModelFactory
import com.example.githubuserproject.repositories.MainViewModel
import com.example.githubuserproject.repositories.ThemeViewModel
import com.example.githubuserproject.ui.FavoriteActivity
import com.example.githubuserproject.ui.ThemeActivity

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding

    private lateinit var mainVm : MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mainVm = ViewModelProvider(this).get(MainViewModel::class.java)
//        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(
//            ThemeViewModel::class.java
//        )

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeVm : ThemeViewModel by viewModels {
            ThemeViewModelFactory(pref)
        }

        themeVm.getThemeSettings().observe(this){isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding?.searchview?.setupWithSearchBar(binding!!.searchbar)
        binding?.searchview
            ?.editText
            ?.setOnEditorActionListener { v, actionId, event ->
                var searchText = binding!!.searchview.text.toString()
                binding!!.searchbar.setText(searchText)
                binding!!.searchview.hide()
                if(searchText == ""){
                    mainVm.searchUser("nanda")
                }else{
                    mainVm.searchUser(searchText)
                }

                true
            }


        val layoutManager = LinearLayoutManager(this)
        binding?.rvListuser?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvListuser?.addItemDecoration(itemDecoration)

        mainVm.user.observe(this){
            setUserRV(it)
            setLoading(false)
        }

        mainVm.loading.observe(this){
            setLoading(it)
        }

        mainVm.errorToastMessage.observe(this) {
            it?.let {
                showToast(it)
            }
        }

        binding?.keFav?.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        binding?.keTema?.setOnClickListener {
            val intent = Intent(this, ThemeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setUserRV(user: List<User>) {
        val adapter = UserAdapter(user)
        binding?.rvListuser?.adapter = adapter
    }

    private fun setLoading(isLoading : Boolean){
        binding?.progressBar?.visibility  = if(isLoading) View.VISIBLE else View.GONE
        binding?.searchbar?.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding?.rvListuser?.visibility  = if (isLoading) View.GONE else View.VISIBLE

    }

}