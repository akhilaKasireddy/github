package com.example.practisedemo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practisedemo.R
import com.example.practisedemo.apiData.Item
import com.example.practisedemo.databinding.ListlitemBinding
import kotlin.reflect.KFunction1

class RecyclerViewAdapter(user: PagingData<Item>, diffCallback: KFunction1<Item, Unit>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private lateinit var binding: ListlitemBinding
    private lateinit var context: Context
    private val users = user
    private lateinit var onItemClicked: (Item) -> Unit
    object DataDiff: DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id==newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.listlitem,
            parent,
            false
        )
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val myItem = users[position]
        holder.textView.text = myItem.login
        Glide.with(context)
            .load(myItem.avatar_url)
            .into(holder.image)
        holder.view.setOnClickListener {
            onItemClicked(myItem)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder(binding: ListlitemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageview
        val textView = binding.textview
        var view = binding.cardView

    }
    private val data = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            Log.d("my tag", "$oldItem , $newItem")
            return oldItem == newItem
        }

    }


}

