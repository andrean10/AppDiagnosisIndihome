package com.kontrakanprojects.appbekamcbr.model.result

import com.google.gson.annotations.SerializedName
import com.kontrakanprojects.appbekamcbr.model.disease.Disease

data class ResponseResult(

	@field:SerializedName("result")
	val result: List<ResultItem>? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CaseMethod(

	@field:SerializedName("disease")
	val disease: Any? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("id_kasus")
	val idKasus: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ResultItem(

	@field:SerializedName("id_konsultasi_hasil")
	val idKonsultasiHasil: String? = null,

	@field:SerializedName("disease")
	val disease: Disease? = null,

	@field:SerializedName("nilai")
	val nilai: String? = null,

	@field:SerializedName("id_konsultasi")
	val idKonsultasi: Any? = null,

	@field:SerializedName("case")
	val caseMethod: CaseMethod? = null,

	@field:SerializedName("status")
	val status: String? = null
)


