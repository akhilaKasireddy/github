package com.example.practiseDemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practiseDemo.R
import com.example.practiseDemo.apiData.Item
import com.example.practiseDemo.extensions.setImageUrl

class UserAdapter(private val onItemClicked: (Item) -> Unit) :
    PagingDataAdapter<Item, UserAdapter.ItemVIewHolder>(object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id && oldItem.login == newItem.login && oldItem.avatar_url == newItem.avatar_url
                    && oldItem.html_url == newItem.html_url
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }) {
    private lateinit var mContext: Context

    class ItemVIewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val gitHubLink: TextView = view.findViewById(R.id.githublink)
        val imageView: ImageView = view.findViewById(R.id.ivProfile)
        val cv: CardView = view.findViewById(R.id.cardView)
    }

    override fun onBindViewHolder(holder: ItemVIewHolder, position: Int) {

        val user = getItem(position)

        if (user != null) {
            holder.gitHubLink.text = user.html_url
            holder.title.text = user.login

            holder.imageView.setImageUrl(user.avatar_url)

            holder.cv.setOnClickListener {
                onItemClicked(user)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVIewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.listlitem, parent, false)
        mContext = parent.context
        return ItemVIewHolder(view)
    }


}