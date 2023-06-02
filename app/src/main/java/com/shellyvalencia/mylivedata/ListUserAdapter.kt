package com.shellyvalencia.mylivedata

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*

class ListUserAdapter(private val listUser: List<ItemsItem>) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private var filteredListUser = listUser.toMutableList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = filteredListUser[position]
        Glide.with(viewHolder.itemView.context)
            .load(item.avatarUrl)
            .into(viewHolder.ivPicture)
        viewHolder.tvItem.text = item.login
    
        viewHolder.itemView.setOnClickListener {
            val intentDetail = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_user", listUser[viewHolder.adapterPosition])
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = filteredListUser.size

    fun filter(query: String) {
        filteredListUser.clear()
        if (query.isEmpty()) {
            filteredListUser.addAll(listUser)
        } else {
            val filteredItems = listUser.filter { it.login.contains(query, ignoreCase = true) }
            filteredListUser.addAll(filteredItems)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tvItem)
        val ivPicture: ImageView = view.findViewById(R.id.ivPicture)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}