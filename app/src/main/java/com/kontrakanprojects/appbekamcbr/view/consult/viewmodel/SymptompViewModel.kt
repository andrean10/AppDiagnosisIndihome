package com.kontrakanprojects.appbekamcbr.view.consult.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kontrakanprojects.appbekamcbr.model.symptoms.ResponseSymptoms

class SymptompViewModel : ViewModel() {

    private var _symptomp: MutableLiveData<ResponseSymptoms>? = null

    fun symptomp(params: HashMap<String, String>): LiveData<ResponseSymptoms> {
        _symptomp = MutableLiveData()
//        addSymptomp(params)
        return _symptomp as MutableLiveData<ResponseSymptoms>
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