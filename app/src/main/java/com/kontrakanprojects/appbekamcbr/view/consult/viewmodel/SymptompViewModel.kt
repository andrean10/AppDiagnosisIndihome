package com.kontrakanprojects.appbekamcbr.view.consult.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kontrakanprojects.appbekamcbr.model.category.ResponseCategory
import com.kontrakanprojects.appbekamcbr.model.symptoms.ResponseSymptoms
import com.kontrakanprojects.appbekamcbr.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SymptompViewModel : ViewModel() {

    private var _symptomp: MutableLiveData<ResponseSymptoms>? = null
    private var _categories: MutableLiveData<ResponseCategory>? = null

    fun symptomp(params: HashMap<String, String>): LiveData<ResponseSymptoms> {
        _symptomp = MutableLiveData()
//        addSymptomp(params)
        return _symptomp as MutableLiveData<ResponseSymptoms>
    }

    fun getCategories(): LiveData<ResponseCategory> {
        _categories = MutableLiveData()
        listSymptompCategory()
        return _categories as MutableLiveData<ResponseCategory>
    }

    fun getSymptomp(idCategory: Int): LiveData<ResponseSymptoms> {
        _symptomp = MutableLiveData()
        listSymptompByCategory(idCategory)
        return _symptomp as MutableLiveData<ResponseSymptoms>
    }

    private fun listSymptompByCategory(idCategory: Int) {
        val client = ApiConfig.getApiService().symptompsByCategory(idCategory)
        client.enqueue(object : Callback<ResponseSymptoms> {
            override fun onResponse(
                call: Call<ResponseSymptoms>,
                response: Response<ResponseSymptoms>
            ) {
                if (response.isSuccessful) {
                    _symptomp?.postValue(response.body())
                } else {
                    val gson = Gson().fromJson(
                        response.errorBody()?.string(),
                        ResponseSymptoms::class.java
                    )
                    _symptomp?.postValue(gson)
                }
            }

            override fun onFailure(call: Call<ResponseSymptoms>, t: Throwable) {
                Log.d("Failure Response ", t.message ?: "")
            }

        })

    }

    private fun listSymptompCategory() {
        val client = ApiConfig.getApiService().symptompCategory()
        client.enqueue(object : Callback<ResponseCategory> {
            override fun onResponse(
                call: Call<ResponseCategory>,
                response: Response<ResponseCategory>
            ) {
                if (response.isSuccessful) {
                    _categories?.postValue(response.body())
                } else {
                    val gson = Gson().fromJson(
                        response.errorBody()?.string(),
                        ResponseCategory::class.java
                    )
                    _categories?.postValue(gson)
                }
            }

            override fun onFailure(call: Call<ResponseCategory>, t: Throwable) {
                Log.e("Failure Response ", t.message ?: "")
            }

        })
    }

//    private fun addSymptomp(params: HashMap<String, String>) {
//        val client = ApiConfig.getApiService().addSymptompConsult(params)
//        client.enqueue(object : Callback<ResponseSymptoms> {
//            override fun onResponse(
//                call: Call<ResponseSymptoms>,
//                response: Response<ResponseSymptoms>
//            ) {
//                if (response.isSuccessful) {
//                    _symptomp?.postValue(response.body())
//                } else {
//                    val error = Gson().fromJson(response.errorBody()?.string(), ResponseSymptoms::class.java)
//                    _symptomp?.postValue(error)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseSymptoms>, t: Throwable) {
//                _symptomp?.postValue(null)
//                Log.e("Failure Response ", t.message ?: "")
//            }
//
//        })
//    }
}