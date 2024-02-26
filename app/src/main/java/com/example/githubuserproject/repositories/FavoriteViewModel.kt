package com.example.githubuserproject.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserproject.data.db.FavoriteUser
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    fun addFavorite(favoriteUser: FavoriteUser){
        viewModelScope.launch {
            favoriteRepository.addFavorite(favoriteUser)
        }
    }

    fun getFavoriteUserByUsername(username : String) = favoriteRepository.getFavoriteUserByUsername(username)

    fun removeFav(username: String){
        viewModelScope.launch {
            favoriteRepository.removeFav(username)
        }
    }

    fun getAllFavUser() = favoriteRepository.getAllFavUser()
}