package com.shellyvalencia.mylivedata.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shellyvalencia.mylivedata.data.remote.response.ItemsItem
import com.shellyvalencia.mylivedata.databinding.ItemUserBinding
import com.shellyvalencia.mylivedata.helper.FavoriteUserDiffCallback
import com.shellyvalencia.mylivedata.ui.detail.DetailActivity
import kotlin.collections.ArrayList

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteUserViewHolder>() {
    private val listFavoriteUsers = ArrayList<ItemsItem>()
    fun submitList(listFavoriteUsers: ArrayList<ItemsItem>) {
        val diffCallback = FavoriteUserDiffCallback(this.listFavoriteUsers, listFavoriteUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUsers.clear()
        this.listFavoriteUsers.addAll(listFavoriteUsers)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        holder.bind(listFavoriteUsers[position])
    }

    override fun getItemCount(): Int {
        return listFavoriteUsers.size
    }

    inner class FavoriteUserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: ItemsItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(favoriteUser.avatarUrl)
                    .into(ivPicture)
                tvItem.text = favoriteUser.login

                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.KEY_USER, favoriteUser)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}