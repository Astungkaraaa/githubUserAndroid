package com.example.githubuserproject.di

import android.content.Context
import com.example.githubuserproject.data.db.FavoriteDatabase
import com.example.githubuserproject.repositories.FavoriteRepository

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        return FavoriteRepository.getInstance(dao)
    }
}