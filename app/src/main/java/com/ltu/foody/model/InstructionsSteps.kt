package com.ltu.foody.model

import com.squareup.moshi.Json

data class InstructionsSteps(
    @Json(name = "step")
    var step:String?
)
