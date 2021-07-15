package com.kontrakanprojects.appbekamcbr.model.category

import com.google.gson.annotations.SerializedName

data class ResponseCategory(

	@field:SerializedName("result")
	val result: List<ResultCategory>? = null,

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)

data class ResultCategory(

	@field:SerializedName("gejala_kategori")
	val gejalaKategori: String? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("id_gejala_kategori")
	val idGejalaKategori: String? = null
)
