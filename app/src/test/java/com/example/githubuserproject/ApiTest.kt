package com.example.githubuserproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githubuserproject.repositories.DetailViewModel
import com.example.githubuserproject.repositories.FollowViewModel
import com.example.githubuserproject.repositories.MainViewModel
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ApiTest {
    private lateinit var mainVm : MainViewModel
    private lateinit var detailVm : DetailViewModel
    private lateinit var followVm : FollowViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mainVm = MainViewModel()
        detailVm = DetailViewModel()
        followVm = FollowViewModel()
    }


    @Test
    fun getAllUsers(){
        val data = mainVm.user.getOrAwaitValue()
        assertThat(data).isNotNull()
    }

    @Test
    fun searchUser(){
        mainVm.searchUser("nanda")
        val search = mainVm.user.getOrAwaitValue()
        assertThat(search).isNotNull()
    }

    @Test
    fun getDetailUser(){
        detailVm.getDetail("Nanda")
        val detail = detailVm.user.getOrAwaitValue()
        assertThat(detail).isNotNull()
    }

    @Test
    fun getFollowers(){
        followVm.getFollowers("nanda")
        val followers = followVm.user.getOrAwaitValue()
        assertThat(followers).isNotNull()
    }

    @Test
    fun getFollowing(){
        followVm.getFollowing("nanda")
        val following = followVm.userFollowing.getOrAwaitValue()
        assertThat(following).isNotNull()
    }



}