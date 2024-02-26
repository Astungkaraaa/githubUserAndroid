package com.example.githubuserproject.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserproject.MainActivity
import com.example.githubuserproject.R
import com.example.githubuserproject.datastore.SettingPreferences
import com.example.githubuserproject.datastore.dataStore
import com.example.githubuserproject.factory.ThemeViewModelFactory
import com.example.githubuserproject.repositories.ThemeViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this){isDarkModeActive ->
            if (isDarkModeActive) {
                setContentView(R.layout.activity_splash_screen_night)
            } else {
                setContentView(R.layout.activity_splash_screen)
            }
        }

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }
}