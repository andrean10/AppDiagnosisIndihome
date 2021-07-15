package com.kontrakanprojects.appbekamcbr.model.disease

import com.google.gson.annotations.SerializedName

data class Disease(
    @field:SerializedName("nm_penyakit")
    val nmPenyakit: String? = null,

    @field:SerializedName("definisi")
    val definisi: String? = null,

    @field:SerializedName("kd_penyakit")
    val kdPenyakit: String? = null,

    @field:SerializedName("id_penyakit")
    val idPenyakit: Int? = null
)