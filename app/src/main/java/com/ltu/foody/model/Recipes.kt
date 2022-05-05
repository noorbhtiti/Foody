package com.ltu.foody.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipes(

    @Json(name = "id")
    var id: Int?,

    @Json(name = "title")
    var title :String?,

    @Json(name = "readyInMinutes")
    var readyInMinutes : Float?,

    @Json(name = "image")
    var image : String?,

    @Json(name = "summary")
    var summary : String?,

    @Json(name = "servings")
    var servings : Int?,



): Parcelable
