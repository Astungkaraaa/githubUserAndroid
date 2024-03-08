package com.example.githubuserproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserproject.data.response.ResponseAllUser
import com.example.githubuserproject.data.response.User
import com.example.githubuserproject.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

class MainViewModel : ViewModel() {

    private val _user = MutableLiveData<List<User>>()
    val user : LiveData<List<User>> = _user

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _errorToastMessage = MutableLiveData<String>()
    val errorToastMessage: LiveData<String> = _errorToastMessage

    init {
        getAllUsers()
    }

    private fun getAllUsers(){
        _loading.value = true
        val datas = ApiConfig.getApiService().getAllUsers("nanda")
        datas.enqueue(object : Callback<ResponseAllUser> {
            override fun onResponse(call: Call<ResponseAllUser>, response: Response<ResponseAllUser>) {
                if(response.isSuccessful){
                    _loading.value = false
                    _user.value = response.body()?.items
                }else{
                    _errorToastMessage.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ResponseAllUser>, t: Throwable) {
                when (t) {
                    is UnknownHostException -> _errorToastMessage.value = "Tidak ada koneksi internet"
                    is ConnectException -> _errorToastMessage.value = "Gagal terhubung ke server"
                    else -> _errorToastMessage.value = "Terjadi kesalahan: ${t.message}"
                }
            }

        })
    }

    fun searchUser(name : String){
        _loading.value = true
        val datas = ApiConfig.getApiService().searchUser(name)
        datas.enqueue(object : Callback<ResponseAllUser> {
            override fun onResponse(call: Call<ResponseAllUser>, response: Response<ResponseAllUser>) {
                if(response.isSuccessful){
                    _loading.value = false
                    _user.value = response.body()?.items
                    if (response.body()?.items?.isEmpty() == true){
                        _errorToastMessage.value = "User tidak ditemukan"
                    }
                }else{
                    _errorToastMessage.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ResponseAllUser>, t: Throwable) {
                when (t) {
                    is UnknownHostException -> _errorToastMessage.value = "Tidak ada koneksi internet"
                    is ConnectException -> _errorToastMessage.value = "Gagal terhubung ke server"
                    else -> _errorToastMessage.value = "Terjadi kesalahan: ${t.message}"
                }
            }
        })
    }



}