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
import com.bumptech.glide.Glide
import com.example.practiseDemo.R
import com.example.practiseDemo.apiData.Item


class UserAdapter() :
        PagingDataAdapter<Item, UserAdapter.ItemVIewHolder>(object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.id == newItem.id && oldItem.login == newItem.login && oldItem.avatar_url == newItem.avatar_url
                        && oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }) {
        private  lateinit var mContext:Context

        class ItemVIewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.title)
            val gitHubLink: TextView = view.findViewById(R.id.textview)
            val imageView: ImageView = view.findViewById(R.id.imageview)
            val cv: CardView = view.findViewById(R.id.card_view)
        }

        override fun onBindViewHolder(holder: ItemVIewHolder, position: Int) {

            val user = getItem(position)

            if (user != null) {
                holder.gitHubLink.text = user.url
                holder.title.text = user.login
                Glide.with(mContext)
                    .load(user.avatar_url)
                    .into(holder.imageView)
                holder.cv.setOnClickListener {
//                    onItemClicked(user)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVIewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.listlitem, parent, false)
            mContext=parent.context
            return ItemVIewHolder(view)
        }


    }