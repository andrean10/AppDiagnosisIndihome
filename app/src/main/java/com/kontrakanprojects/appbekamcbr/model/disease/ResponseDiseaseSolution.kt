package com.kontrakanprojects.appbekamcbr.model.disease

import com.google.gson.annotations.SerializedName
import com.kontrakanprojects.appbekamcbr.model.solution.Solution

data class ResponseDiseaseSolution(

    @field:SerializedName("result")
    val result: List<ResultsDiseaseSolution>? = null,

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String
)

data class ResultsDiseaseSolution(

    @field:SerializedName("id_penyakit_solusi")
    val idPenyakitSolusi: Int? = null,

    @field:SerializedName("solution")
    val solution: Solution? = null,

    @field:SerializedName("id_penyakit")
    val idPenyakit: Int? = null
)

