package com.kontrakanprojects.appbekamcbr.network

import com.kontrakanprojects.appbekamcbr.model.ResponseDisease
import com.kontrakanprojects.appbekamcbr.model.category.ResponseCategory
import com.kontrakanprojects.appbekamcbr.model.consult.ResponseConsult
import com.kontrakanprojects.appbekamcbr.model.disease.ResponseDiseaseSolution
import com.kontrakanprojects.appbekamcbr.model.solution.ResponseSolution
import com.kontrakanprojects.appbekamcbr.model.symptoms.ResponseSymptoms
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /** DISEASE ROUTE */

    @GET("disease/read.php")
    fun disease(): Call<ResponseDisease>

    @GET("disease/single-read.php")
    fun detailDisease(@Query("id") id: Int): Call<ResponseDiseaseSolution>

    /** SOLUTION/TERAPI ROUTE */

    @GET("solution/read.php")
    fun solution(): Call<ResponseSolution>

    @GET("disease_solution/read.php")
    fun detailSolution(@Query("id") id: Int): Call<ResponseSolution>

    /** SYMPTOMP ROUTE */

    @GET("symptomp/read.php")
    fun symptompsByCategory(@Query("id") id: Int): Call<ResponseSymptoms>

    /** SYMPTOMP-CATEGORY ROUTE */
    @GET("symptomp_category/read.php")
    fun symptompCategory(): Call<ResponseCategory>

    /** GEJALA CONSULT ROUTE */

    @GET("symptomp_consultation/read.php")
    fun symptompConsult(@Query("id_konsultasi") id: Int): Call<ResponseConsult>

    @FormUrlEncoded
    @POST("symptomp_consultation/create.php")
    fun addSymptompConsult(@FieldMap params: HashMap<String, String>): Call<ResponseConsult>

    @FormUrlEncoded
    @POST("symptomp_consultation/update.php")
    fun updateConsult(@FieldMap params: HashMap<String, String>): Call<ResponseSymptoms>

    /** CONSULT ROUTE */

    @GET("consultation-result/read.php")
    fun resultConsult(): Call<ResponseConsult>

    @FormUrlEncoded
    @POST("consultation/create.php")
    fun addConsult(@Field("nama_konsul") consultName: String): Call<ResponseConsult>
}