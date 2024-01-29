package com.shellyvalencia.mylivedata.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shellyvalencia.mylivedata.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favoriteUser")
    fun getFavoritedUser(): LiveData<List<FavoriteUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

    @Query("SELECT EXISTS(SELECT * FROM FavoriteUser WHERE username = :username)")
    fun isFavorited(username: String): LiveData<Boolean>
}