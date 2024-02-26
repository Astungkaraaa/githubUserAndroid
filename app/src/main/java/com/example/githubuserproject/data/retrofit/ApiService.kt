package com.example.githubuserproject.data.retrofit

import com.example.githubuserproject.data.response.FollowUserItem
import com.example.githubuserproject.data.response.ResponseAllUser
import com.example.githubuserproject.data.response.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getAllUsers(
        @Query("q") query : String
    ): Call<ResponseAllUser>

    @GET("search/users")
    fun searchUser(
        @Query("q") query : String
    ): Call<ResponseAllUser>

    @GET("users/{nama}")
    fun getUserDetail(
        @Path("nama") nama : String
    ): Call<UserDetail>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<FollowUserItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<FollowUserItem>>
}