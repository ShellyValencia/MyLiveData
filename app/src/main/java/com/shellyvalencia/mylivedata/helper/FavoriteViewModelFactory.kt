package com.shellyvalencia.mylivedata.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shellyvalencia.mylivedata.ui.favorite.FavoriteViewModel
import com.shellyvalencia.mylivedata.ui.insert.FavoriteUserAddUpdateViewModel

class FavoriteViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: FavoriteViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteViewModelFactory::class.java) {
                    INSTANCE = FavoriteViewModelFactory(application)
                }
            }
            return INSTANCE as FavoriteViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteUserAddUpdateViewModel::class.java)) {
            return FavoriteUserAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}