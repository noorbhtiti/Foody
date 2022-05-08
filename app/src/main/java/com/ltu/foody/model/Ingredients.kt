package com.ltu.foody.model

import com.squareup.moshi.Json

data class Ingredients(
    @Json(name = "original")
    var original: String?,

    )
