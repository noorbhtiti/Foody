package com.ltu.foody.model

import com.squareup.moshi.Json

data class Instructions(
    @Json(name = "steps")
    var steps : List<InstructionsSteps> = listOf()
)
