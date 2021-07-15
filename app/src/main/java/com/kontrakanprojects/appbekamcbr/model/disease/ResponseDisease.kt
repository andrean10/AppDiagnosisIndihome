package com.kontrakanprojects.appbekamcbr.model

import com.google.gson.annotations.SerializedName
import com.kontrakanprojects.appbekamcbr.model.disease.Disease

data class ResponseDisease(

    @field:SerializedName("result")
    val result: List<Disease>? = null,

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String
)

