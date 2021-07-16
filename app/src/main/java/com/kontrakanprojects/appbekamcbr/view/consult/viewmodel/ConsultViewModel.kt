package com.kontrakanprojects.appbekamcbr.view.consult.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kontrakanprojects.appbekamcbr.model.consult.ResponseConsult
import com.kontrakanprojects.appbekamcbr.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsultViewModel : ViewModel() {

    private var _consult: MutableLiveData<ResponseConsult>? = null

    fun consult(name: String): LiveData<ResponseConsult> {
        _consult = MutableLiveData()
        addConsult(name)
        return _consult as MutableLiveData<ResponseConsult>
    }

    private fun addConsult(name: String) {
        val client = ApiConfig.getApiService().addConsult(name)
        client.enqueue(object : Callback<ResponseConsult> {
            override fun onResponse(
                call: Call<ResponseConsult>,
                response: Response<ResponseConsult>
            ) {
                if (response.isSuccessful) {
                    _consult?.postValue(response.body())
                } else {
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ResponseConsult::class.java)
                    _consult?.postValue(error)
                }
            }

            override fun onFailure(call: Call<ResponseConsult>, t: Throwable) {
                _consult?.postValue(null)
                Log.e("Failure Response ", t.message ?: "")
            }

        })
    }
}