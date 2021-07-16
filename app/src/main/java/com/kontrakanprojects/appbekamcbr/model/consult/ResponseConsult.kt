package com.kontrakanprojects.appbekamcbr.model.consult

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ResponseConsult(

	@field:SerializedName("result")
	val result: ResultConsult? = null,

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class ResultConsult(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id_konsultasi")
	val idKonsultasi: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null
) : Parcelable
