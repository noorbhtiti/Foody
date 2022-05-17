package com.ltu.foody.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("posterImageUrl")
fun posterImage(imgView: ImageView,imgUrl:String?){
    imgUrl?.let{ posterPath ->
        Glide
            .with(imgView)
            .load(imgUrl)
            .into(imgView)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("servings")
fun servings(txt:TextView, content:String) {
    content.let {
        txt.text = "Servings for: $it person(s)"
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("readyInMinutes")
fun readyInMinutes(txt:TextView, content:String) {
    content.let {
        txt.text = "Ready in: $it minutes"
    }
}
