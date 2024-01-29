package com.shellyvalencia.mylivedata.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shellyvalencia.mylivedata.data.remote.response.GithubResponse
import com.shellyvalencia.mylivedata.data.remote.response.ItemsItem
import com.shellyvalencia.mylivedata.data.remote.retrofit.ApiConfig
import com.shellyvalencia.mylivedata.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object{
        private const val TAG = "MainViewModel"
        private const val GITHUB_USERNAME = "Arif"
    }

    init {
        findUser()
    }

    internal fun findUser(searchTerm: String = "") {
        _isLoading.value = true
        _listUser.value = emptyList()
        val client = ApiConfig.getApiService().getListUsers(searchTerm.ifEmpty { GITHUB_USERNAME })
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}