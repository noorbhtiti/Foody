package com.ltu.foody.network

import com.ltu.foody.model.Ingredients
import com.ltu.foody.model.Instructions
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MealDetailsResponse {
    @Json(name = "vegan")
    var vegan: Boolean = false

    @Json(name = "glutenFree")
    var glutenFree: Boolean = false

    @Json(name = "dairyFree")
    var dairyFree: Boolean = false

    @Json(name = "title")
    lateinit var title: String

    @Json(name = "sourceUrl")
    lateinit var sourceUrl:String

    @Json(name = "image")
    lateinit var image:String

    @Json(name = "extendedIngredients")
    var extendedIngredients: List<Ingredients> = listOf()

    @Json(name = "analyzedInstructions")
    var analyzedInstructions: List<Instructions> = listOf()








}