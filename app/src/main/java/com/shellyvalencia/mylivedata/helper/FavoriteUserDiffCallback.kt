package com.shellyvalencia.mylivedata.helper

import androidx.recyclerview.widget.DiffUtil
import com.shellyvalencia.mylivedata.data.remote.response.ItemsItem
import kotlin.collections.ArrayList

class FavoriteUserDiffCallback(private val oldFavoriteUserList: ArrayList<ItemsItem>, private val newFavoriteUserList: ArrayList<ItemsItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteUserList.size
    override fun getNewListSize(): Int = newFavoriteUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteUserList[oldItemPosition].login == newFavoriteUserList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteUser = oldFavoriteUserList[oldItemPosition]
        val newFavoriteUser = newFavoriteUserList[newItemPosition]
        return oldFavoriteUser.login == newFavoriteUser.login && oldFavoriteUser.avatarUrl == newFavoriteUser.avatarUrl
    }
}