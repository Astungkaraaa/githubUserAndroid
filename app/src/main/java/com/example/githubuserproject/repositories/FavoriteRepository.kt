package com.example.githubuserproject.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.githubuserproject.Result
import com.example.githubuserproject.data.db.FavoriteDao
import com.example.githubuserproject.data.db.FavoriteUser

class FavoriteRepository private constructor(private val favoriteDao: FavoriteDao) {

    suspend fun addFavorite(favoriteUser: FavoriteUser){
        favoriteDao.save(favoriteUser)
    }

    fun getFavoriteUserByUsername(username : String) : LiveData<FavoriteUser>{
        return favoriteDao.getFavoriteUserByUsername(username)
    }

    suspend fun removeFav(username: String){
        favoriteDao.removeFav(username)
    }

    fun getAllFavUser() : LiveData<Result<List<FavoriteUser>>> = liveData{
        emit(Result.Loading)
        val favUserLiveData = favoriteDao.getAllFavUser()
        val transformedLiveData = favUserLiveData.map { userList ->
            Log.d(FavoriteRepository::class.java.simpleName, "favUserTes : ${userList.size}")
            if (userList.isNotEmpty()) {
                Result.Success(userList)
            } else {
                Result.Error("Tidak ada data yang ditemukan")
            }
        }

        try {
            emitSource(transformedLiveData)
        } catch (e: Exception) {
            emit(Result.Error("Gagal mengambil data: ${e.message}"))
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteDao)
            }.also { instance = it }
    }

}