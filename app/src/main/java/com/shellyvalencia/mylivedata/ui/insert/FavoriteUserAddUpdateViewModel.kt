package com.shellyvalencia.mylivedata.ui.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shellyvalencia.mylivedata.data.local.entity.FavoriteUser
import com.shellyvalencia.mylivedata.data.FavoriteUserRepository

class FavoriteUserAddUpdateViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun isFavorited(username: String): LiveData<Boolean> = mFavoriteUserRepository.isFavorited(username)

}