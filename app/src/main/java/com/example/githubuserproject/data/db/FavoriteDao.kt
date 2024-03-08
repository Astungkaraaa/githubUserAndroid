package com.example.githubuserproject.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favoriteuser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

    @Query("DELETE FROM favoriteuser WHERE username = :username")
    suspend fun removeFav(username: String)

    @Query("SELECT * FROM favoriteuser")
    fun getAllFavUser(): LiveData<List<FavoriteUser>>

}