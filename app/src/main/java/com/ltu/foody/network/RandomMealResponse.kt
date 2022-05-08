package com.ltu.foody.network

import com.ltu.foody.model.Recipes
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RandomMealResponse {

    @Json(name = "recipes")
    var recipes: List<Recipes> = listOf()
}