package com.shellyvalencia.mylivedata.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shellyvalencia.mylivedata.R
import com.shellyvalencia.mylivedata.data.remote.response.ItemsItem
import com.shellyvalencia.mylivedata.ui.detail.DetailActivity

class ListUserAdapter(private val listUser: List<ItemsItem>) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tvItem)
        val ivPicture: ImageView = view.findViewById(R.id.ivPicture)
    }

}