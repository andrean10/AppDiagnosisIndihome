package com.kontrakanprojects.appbekamcbr.model.consult

import com.google.gson.annotations.SerializedName

data class ResponseConsult(

	@field:SerializedName("result")
	val result: ResultConsult? = null,

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)

data class ResultConsult(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id_konsultasi")
	val idKonsultasi: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null
)
