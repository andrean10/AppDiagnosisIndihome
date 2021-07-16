package com.kontrakanprojects.appbekamcbr.view.info.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kontrakanprojects.appbekamcbr.model.ResponseDisease
import com.kontrakanprojects.appbekamcbr.model.solution.ResponseSolution
import com.kontrakanprojects.appbekamcbr.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoViewModel : ViewModel() {
    private var _diseases: MutableLiveData<ResponseDisease>? = null
    private var _solutions: MutableLiveData<ResponseSolution>? = null

    fun getListDisease(): LiveData<ResponseDisease> {
        _diseases = MutableLiveData<ResponseDisease>()
        listDisease()
        return _diseases as MutableLiveData<ResponseDisease>
    }

    fun getListSolution(): LiveData<ResponseSolution> {
        _solutions = MutableLiveData<ResponseSolution>()
        listSolution()
        return _solutions as MutableLiveData<ResponseSolution>
    }

    private fun listSolution() {
        val client = ApiConfig.getApiService().solution()
        client.enqueue(object : Callback<ResponseSolution> {
            override fun onResponse(
                call: Call<ResponseSolution>,
                response: Response<ResponseSolution>
            ) {
                if (response.isSuccessful) {
                    _solutions?.postValue(response.body())
                } else {
                    val error = Gson().fromJson(
                        response.errorBody()?.string(),
                        ResponseSolution::class.java
                    )
                    _solutions?.postValue(error)
                }
            }

            override fun onFailure(call: Call<ResponseSolution>, t: Throwable) {
                Log.e("Failure Response ", t.message ?: "")
            }
        })
    }

    private fun listDisease() {
        val client = ApiConfig.getApiService().disease()
        client.enqueue(object : Callback<ResponseDisease> {
            override fun onResponse(
                call: Call<ResponseDisease>,
                response: Response<ResponseDisease>
            ) {
                if (response.isSuccessful) {
                    _diseases?.postValue(response.body())
                } else {
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ResponseDisease::class.java)
                    _diseases?.postValue(error)
                }
            }

            override fun onFailure(call: Call<ResponseDisease>, t: Throwable) {
                _diseases?.postValue(null)
                Log.e("Failure Response ", t.message ?: "")
            }
        })
    }
}