package com.kontrakanprojects.appbekamcbr.view.info.daftarsolusi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kontrakanprojects.appbekamcbr.model.solution.ResponseSolution
import com.kontrakanprojects.appbekamcbr.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarSolusiViewModel: ViewModel() {
    private var _solutions: MutableLiveData<ResponseSolution>? = null
    
    fun getListSolution(): LiveData<ResponseSolution>{
        _solutions = MutableLiveData<ResponseSolution>()
        listSolution()
        return _solutions as MutableLiveData<ResponseSolution>
    }

    private fun listSolution() {
        val client = ApiConfig.getApiService().solution()
        client.enqueue(object : Callback<ResponseSolution> {
            override fun onResponse(call: Call<ResponseSolution>, response: Response<ResponseSolution>) {
                if(response.isSuccessful){
                    _solutions?.postValue(response.body())
                }else{
                    val error = Gson().fromJson(response.errorBody()?.string(),ResponseSolution::class.java)
                    _solutions?.postValue(error)
                }
            }

            override fun onFailure(call: Call<ResponseSolution>, t: Throwable) {
                Log.e("Failure Response ", t.message ?: "")
            }
        })
    }
}