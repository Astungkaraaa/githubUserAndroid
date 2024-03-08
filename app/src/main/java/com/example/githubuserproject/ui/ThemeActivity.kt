package com.example.githubuserproject.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.githubuserproject.R
import com.example.githubuserproject.databinding.ActivityThemeBinding
import com.example.githubuserproject.datastore.SettingPreferences
import com.example.githubuserproject.datastore.dataStore
import com.example.githubuserproject.factory.ThemeViewModelFactory
import com.example.githubuserproject.ui.viewmodel.ThemeViewModel

class ThemeActivity : AppCompatActivity() {
    private var _binding : ActivityThemeBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel : ThemeViewModel by viewModels {
            ThemeViewModelFactory(pref)
        }

        themeViewModel.getThemeSettings().observe(this){isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding?.switchTheme?.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding?.switchTheme?.isChecked = false
            }
        }

        binding?.switchTheme?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean  ->
            themeViewModel.saveThemeSetting(isChecked)
            if (isChecked == true){
                binding?.hintDarkmode?.text = getString(R.string.darkmode_diaktifkan)
            }
        }

        binding?.backbtntheme?.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}