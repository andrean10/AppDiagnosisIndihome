package com.kontrakanprojects.appbekamcbr.view.info.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kontrakanprojects.appbekamcbr.model.disease.ResponseDiseaseSolution
import com.kontrakanprojects.appbekamcbr.model.solution.ResponseSolution
import com.kontrakanprojects.appbekamcbr.network.ApiConfig
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DetailViewModel: ViewModel() {
    private var _diseaseSolution: MutableLiveData<ResponseDiseaseSolution>? = null

    fun getDiseaseSolution(idDisease: Int){
        _diseaseSolution = MutableLiveData<ResponseDiseaseSolution>()
        diseaseSolution(idDisease)
    }

    private fun diseaseSolution(idDisease: Int){
        val client = ApiConfig.getApiService().detailDisease(idDisease)
        client.enqueue(object : retrofit2.Callback<ResponseDiseaseSolution> {
            override fun onResponse(call: Call<ResponseDiseaseSolution>, response: Response<ResponseDiseaseSolution>) {
                if (response.isSuccessful) {
                    _diseaseSolution?.postValue(response.body())
                } else {
                    val error = Gson().fromJson(response.errorBody()?.string(), ResponseDiseaseSolution::class.java)
                    _diseaseSolution?.postValue(error)
                }
            }

            override fun onFailure(call: Call<ResponseDiseaseSolution>, t: Throwable) {
                Log.e("Failure Response ", t.message ?: "")
            }
        })
    }
}