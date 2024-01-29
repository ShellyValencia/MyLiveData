package com.shellyvalencia.mylivedata.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shellyvalencia.mylivedata.data.local.entity.FavoriteUser
import com.shellyvalencia.mylivedata.data.FavoriteUserRepository
import com.shellyvalencia.mylivedata.util.Event

class FavoriteViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    fun getFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getFavoritedUser()
}