package com.example.githubuserproject.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserproject.data.response.UserDetail
import com.example.githubuserproject.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

class DetailViewModel : ViewModel() {
    private val _user = MutableLiveData<UserDetail>()
    val user : LiveData<UserDetail> = _user

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _errorToastMessage = MutableLiveData<String>()
    val errorToastMessage: LiveData<String> = _errorToastMessage

    fun getDetail(nama : String) {
        _loading.value = true
        val datas = ApiConfig.getApiService().getUserDetail(nama)
        datas.enqueue(object : Callback<UserDetail>{
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                if(response.isSuccessful){
                    _loading.value = false
                    _user.value = response.body()
                    if (response.body()?.followers == 0){
                        _errorToastMessage.value = "User tidak memiliki followers"
                    }else if (response.body()?.following == 0){
                        _errorToastMessage.value = "User tidak memiliki following"
                    } else if (response.body()?.following == 0 && response.body()?.followers == 0){
                        _errorToastMessage.value = "User tidak memiliki followers dan following"
                    }
                }else{
                    _errorToastMessage.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                when (t) {
                    is UnknownHostException -> _errorToastMessage.value = "Tidak ada koneksi internet"
                    is ConnectException -> _errorToastMessage.value = "Gagal terhubung ke server"
                    else -> _errorToastMessage.value = "Terjadi kesalahan: ${t.message}"
                }
            }
        })
    }


}