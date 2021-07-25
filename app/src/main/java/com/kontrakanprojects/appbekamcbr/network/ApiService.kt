package com.kontrakanprojects.appbekamcbr.network

import com.kontrakanprojects.appbekamcbr.model.ResponseDisease
import com.kontrakanprojects.appbekamcbr.model.category.ResponseCategory
import com.kontrakanprojects.appbekamcbr.model.consult.ResponseConsult
import com.kontrakanprojects.appbekamcbr.model.disease.ResponseDiseaseSolution
import com.kontrakanprojects.appbekamcbr.model.result.ResponseResult
import com.kontrakanprojects.appbekamcbr.model.solution.ResponseSolution
import com.kontrakanprojects.appbekamcbr.model.symptoms.ResponseSymptoms
import com.kontrakanprojects.appbekamcbr.model.symptoms_consult.ResponseSymptomConsult
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /** DISEASE ROUTE */

    @GET("disease/read.php")
    fun disease(): Call<ResponseDisease>

    @GET("disease/single-read.php")
    fun detailDisease(@Query("id") id: String): Call<ResponseDiseaseSolution>

    /** SOLUTION/TERAPI ROUTE */

    @GET("solution/read.php")
    fun solution(): Call<ResponseSolution>

    /** SYMPTOMP ROUTE */

    @GET("symptomp/read.php")
    fun symptompsByCategory(
        @Query("kategori") id_kategori: String,
        @Query("konsultasi") id_konsultasi: String
    ): Call<ResponseSymptoms>

    /** SYMPTOMP-CATEGORY ROUTE */
    @GET("symptomp_category/read.php")
    fun symptompCategory(): Call<ResponseCategory>

    /** GEJALA CONSULT ROUTE */

    @GET("symptomp_consultation/read.php")
    fun symptompConsult(@Query("id_konsultasi") id: String): Call<ResponseConsult>

    @FormUrlEncoded
    @POST("symptomp_consultation/create.php")
    fun addOrUpdateSymptompConsult(
        @Field("list_id_gejala[]") listIdSymptoms: ArrayList<String>,
        @Field("id_konsultasi") id: String
    ): Call<ResponseSymptomConsult>

    @DELETE("symptomp_consultation/delete.php/{konsultasi}")
    fun resetConsult(
        @Path("konsultasi") id_konsultasi: String
    ): Call<ResponseConsult>


    /** CONSULT ROUTE */

    @GET("consultation-result/read.php")
    fun resultConsult(@Query("konsultasi") idConsult: String): Call<ResponseResult>

    @FormUrlEncoded
    @POST("consultation/create.php")
    fun addConsult(@Field("nama_konsul") consultName: String): Call<ResponseConsult>
}