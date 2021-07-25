package com.kontrakanprojects.appbekamcbr.view.consult.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kontrakanprojects.appbekamcbr.model.category.ResponseCategory
import com.kontrakanprojects.appbekamcbr.model.symptoms.ResponseSymptoms
import com.kontrakanprojects.appbekamcbr.model.symptoms.ResultSymptoms
import com.kontrakanprojects.appbekamcbr.model.symptoms_consult.ResponseSymptomConsult
import com.kontrakanprojects.appbekamcbr.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SymptompViewModel : ViewModel() {

    private var _symptomp: MutableLiveData<ResponseSymptoms>? = null
    private var _symptompsConsult: MutableLiveData<ResponseSymptomConsult>? = null
    private var _categories: MutableLiveData<ResponseCategory>? = null
    private var _selectedIds: MutableLiveData<ArrayList<String>> = MutableLiveData()
    private var _selectedSymptomps: MutableLiveData<ArrayList<ResultSymptoms>> = MutableLiveData()

    private val TAG = SymptompViewModel::class.simpleName

    fun symptompConsult(
        listSelectedIdSymp: ArrayList<String>,
        idConsult: String
    ): LiveData<ResponseSymptomConsult> {
        _symptompsConsult = MutableLiveData()
        addSymptomp(listSelectedIdSymp, idConsult)
        return _symptompsConsult as MutableLiveData<ResponseSymptomConsult>
    }

    fun setSelectedSymptomps(selectedSymptoms: ArrayList<ResultSymptoms>) {
        _selectedSymptomps.postValue((selectedSymptoms))
    }

    fun setSelectedIdsSymtomps(idsSelectedSymps: ArrayList<String>) {
        _selectedIds.postValue(idsSelectedSymps)
    }

    fun getSelectedIdsSymptomps(): LiveData<ArrayList<String>> {
        return _selectedIds as MutableLiveData<ArrayList<String>>
    }

    fun getSelectedSymptomps(): LiveData<ArrayList<ResultSymptoms>> {
        return _selectedSymptomps as MutableLiveData<ArrayList<ResultSymptoms>>
    }


    fun getCategories(): LiveData<ResponseCategory> {
        _categories = MutableLiveData()
        listSymptompCategory()
        return _categories as MutableLiveData<ResponseCategory>
    }

    fun getSymptomp(idCategory: String, idConsult: String): LiveData<ResponseSymptoms> {
        _symptomp = MutableLiveData()
        listSymptompByCategory(idCategory, idConsult)
        return _symptomp as MutableLiveData<ResponseSymptoms>
    }

    private fun listSymptompByCategory(idCategory: String, idConsult: String) {
        val client = ApiConfig.getApiService().symptompsByCategory(idCategory, idConsult)
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
                _symptomp?.postValue(null)
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
                _symptomp?.postValue(null)
                Log.e("Failure Response ", t.message ?: "")
            }

        })
    }

    private fun addSymptomp(listIdSymptom: ArrayList<String>, idConsult: String) {
        val client = ApiConfig.getApiService().addOrUpdateSymptompConsult(listIdSymptom, idConsult)
        client.enqueue(object : Callback<ResponseSymptomConsult> {
            override fun onResponse(
                call: Call<ResponseSymptomConsult>,
                response: Response<ResponseSymptomConsult>
            ) {
                if (response.isSuccessful) {
                    _symptompsConsult?.postValue(response.body())
                    Log.d("Symptomp consult add: ", _symptompsConsult.toString())
                } else {
                    val error = Gson().fromJson(
                        response.errorBody()?.string(),
                        ResponseSymptomConsult::class.java
                    )
                    _symptompsConsult?.postValue(error)
                }
            }

            override fun onFailure(call: Call<ResponseSymptomConsult>, t: Throwable) {
                _symptomp?.postValue(null)
                Log.e("Failure Response ", t.message ?: "")
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: Dipanggil")
    }
}