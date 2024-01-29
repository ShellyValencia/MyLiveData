package com.shellyvalencia.mylivedata.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.shellyvalencia.mylivedata.ui.setting.SettingPreferences
import com.shellyvalencia.mylivedata.ui.setting.SettingViewModel

class SettingViewModelFactory(private val pref: SettingPreferences) : NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}