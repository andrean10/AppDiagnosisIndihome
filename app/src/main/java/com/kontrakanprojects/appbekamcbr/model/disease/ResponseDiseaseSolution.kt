package com.kontrakanprojects.appbekamcbr.model.disease

import com.google.gson.annotations.SerializedName
import com.kontrakanprojects.appbekamcbr.model.solution.Solution

data class ResponseDiseaseSolution(

    @field:SerializedName("result")
    val result: List<ResultItem?>? = null,

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class ResultItem(

    @field:SerializedName("id_penyakit_solusi")
    val idPenyakitSolusi: Int? = null,

    @field:SerializedName("solution")
    val solution: Solution? = null,

    @field:SerializedName("id_penyakit")
    val idPenyakit: Int? = null
)

