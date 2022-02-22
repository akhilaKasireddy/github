//package com.example.practiseDemo.dataBaseAdapter

//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.practiseDemo.R
//import com.example.practiseDemo.dao.UserDbData
//import com.example.practiseDemo.databinding.ListlitemBinding
//
//class DataBaseAdapter(user: List<UserTable>, val onItemClicked: (UserTable) -> Unit) :
//    RecyclerView.Adapter<DataBaseAdapter.ViewHolder>() {
//    private lateinit var binding: ListlitemBinding
//    private lateinit var context: Context
//    private val users = user
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        binding = DataBindingUtil.inflate(
//            LayoutInflater.from(parent.context),
//            R.layout.listlitem,
//            parent,
//            false
//        )
//        context = parent.context
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        val myItem = users[position]
//        holder.textView.text = myItem.userName
//        Glide.with(context)
//            .load(myItem.userImage)
//            .into(holder.image)
//        holder.view.setOnClickListener {
//            onItemClicked(myItem)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return users.size
//    }
//
//    class ViewHolder(binding: ListlitemBinding) : RecyclerView.ViewHolder(binding.root) {
//        val image = binding.imageview
//        val textView = binding.textview
//        var view = binding.cardView
//
//    }
//}