package com.ltu.foody.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipes")
data class Recipes(

    @PrimaryKey()
    @Json(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    var title :String?,

    @ColumnInfo(name = "readyInMinutes")
    @Json(name = "readyInMinutes")
    var readyInMinutes : Float?,

    @ColumnInfo(name = "image")
    @Json(name = "image")
    var image : String?,

    @ColumnInfo(name = "summary")
    @Json(name = "summary")
    var summary : String?,

    @ColumnInfo(name = "servings")
    @Json(name = "servings")
    var servings : Int?,

): Parcelable
