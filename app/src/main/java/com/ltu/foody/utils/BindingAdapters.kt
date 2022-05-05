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

//@BindingAdapter("BackdropImageUrl")
//fun bindBackdropImage(imgView: ImageView, imgUrl:String) {
//    imgUrl.let { backdropPath ->
//        Glide
//            .with(imgView)
//            .load(Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + backdropPath)
//            .into(imgView)
//    }
//}
//
//@SuppressLint("SetTextI18n")
//@BindingAdapter("ReviewAuthor")
//fun reviewAuthor(txt:TextView, author:String) {
//    author.let {
//        txt.text = "Author:\n$it"
//    }
//}
//
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
