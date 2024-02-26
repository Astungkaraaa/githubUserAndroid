package com.example.githubuserproject.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserproject.data.response.FollowUserItem
import com.example.githubuserproject.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

class FollowViewModel : ViewModel() {
    private val _user = MutableLiveData<List<FollowUserItem>>()
    val user : LiveData<List<FollowUserItem>> = _user

    private val _userFollowing = MutableLiveData<List<FollowUserItem>>()
    val userFollowing : LiveData<List<FollowUserItem>> = _userFollowing

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _errorToastMessage = MutableLiveData<String>()
    val errorToastMessage: LiveData<String> = _errorToastMessage

    companion object{
        private const val TAG = "FollowViewModel"
    }

    fun getFollowers(name : String){
        _loading.value = true
        val datas = ApiConfig.getApiService().getFollowers(name)
        datas.enqueue(object : Callback<List<FollowUserItem>>{
            override fun onResponse(call: Call<List<FollowUserItem>>, response: Response<List<FollowUserItem>>) {
                if(response.isSuccessful){
                    _user.value = response.body()
                    _loading.value = false
                }else{
                    _errorToastMessage.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<FollowUserItem>>, t: Throwable) {
                when (t) {
                    is UnknownHostException -> _errorToastMessage.value = "Tidak ada koneksi internet"
                    is ConnectException -> _errorToastMessage.value = "Gagal terhubung ke server"
                    else -> _errorToastMessage.value = "Terjadi kesalahan: ${t.message}"
                }
            }

        })
    }

    fun getFollowing(name : String){
        _loading.value = true
        val datas = ApiConfig.getApiService().getFollowing(name)
        datas.enqueue(object : Callback<List<FollowUserItem>>{
            override fun onResponse(call: Call<List<FollowUserItem>>, response: Response<List<FollowUserItem>>) {
                if (response.isSuccessful){
                    _userFollowing.value = response.body()
                    _loading.value = false
                }else{
                    _errorToastMessage.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<FollowUserItem>>, t: Throwable) {
                when (t) {
                    is UnknownHostException -> _errorToastMessage.value = "Tidak ada koneksi internet"
                    is ConnectException -> _errorToastMessage.value = "Gagal terhubung ke server"
                    else -> _errorToastMessage.value = "Terjadi kesalahan: ${t.message}"
                }
            }

        })
    }

}