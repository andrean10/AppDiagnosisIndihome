package com.kontrakanprojects.appbekamcbr.model

import com.google.gson.annotations.SerializedName

data class ResponseDisease(

    @field:SerializedName("result")
    val result: List<ResultDisease>? = null,

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String
)

data class ResultDisease(

    @field:SerializedName("nm_penyakit")
    val nmPenyakit: String? = null,

    @field:SerializedName("definisi")
    val definisi: String? = null,

    @field:SerializedName("kd_penyakit")
    val kdPenyakit: String? = null,

    @field:SerializedName("id_penyakit")
    val idPenyakit: String? = null
)
