package com.kontrakanprojects.appbekamcbr.model.solution

import com.google.gson.annotations.SerializedName

data class ResponseSolution(

    @field:SerializedName("result")
    val result: List<ResultSolusi>? = null,

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String
)

data class ResultSolusi(

    @field:SerializedName("keterangan")
    val keterangan: String? = null,

    @field:SerializedName("id_solusi")
    val idSolusi: String? = null,

    @field:SerializedName("kd_solusi")
    val kdSolusi: String? = null,

    @field:SerializedName("nm_solusi")
    val nmSolusi: String? = null
)
