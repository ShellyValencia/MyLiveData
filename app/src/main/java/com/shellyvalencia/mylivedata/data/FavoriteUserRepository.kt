package com.shellyvalencia.mylivedata.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.shellyvalencia.mylivedata.data.local.entity.FavoriteUser
import com.shellyvalencia.mylivedata.data.local.room.FavoriteUserDao
import com.shellyvalencia.mylivedata.data.local.room.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUsersDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUsersDao = db.favoriteUserDao()
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUsersDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUsersDao.delete(favoriteUser) }
    }

    fun getFavoritedUser(): LiveData<List<FavoriteUser>> {
        return mFavoriteUsersDao.getFavoritedUser()
    }

    fun isFavorited(username: String) = mFavoriteUsersDao.isFavorited(username)
}