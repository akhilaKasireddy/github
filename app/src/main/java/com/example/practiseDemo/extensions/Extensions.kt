package com.example.practiseDemo.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String){
    Glide.with(this)
        .load(url)
        .into(this)
}
//
//@BindingAdapter("imageUrl")
//fun setImageUrl(imageView: ImageView, url: String?) {
//    Glide.with(imageView.context).load(url).into(imageView)
//}