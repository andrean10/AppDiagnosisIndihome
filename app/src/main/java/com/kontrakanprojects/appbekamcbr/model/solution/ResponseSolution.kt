package com.kontrakanprojects.appbekamcbr.model.solution

import com.google.gson.annotations.SerializedName

data class ResponseSolution(

    @field:SerializedName("result")
    val result: List<Solution>? = null,

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String
)

