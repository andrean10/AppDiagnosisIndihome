package com.kontrakanprojects.appbekamcbr.view.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kontrakanprojects.appbekamcbr.model.consult.ResponseConsult
import com.kontrakanprojects.appbekamcbr.model.disease.ResponseDiseaseSolution
import com.kontrakanprojects.appbekamcbr.model.result.ResponseResult
import com.kontrakanprojects.appbekamcbr.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultViewModel : ViewModel() {
    private var _result: MutableLiveData<ResponseResult>? = null
    private var _solutions: MutableLiveData<ResponseDiseaseSolution>? = null
    private var _consult: MutableLiveData<ResponseConsult>? = null


    fun result(idConsult: String): LiveData<ResponseResult> {
        _result = MutableLiveData()
        getResult(idConsult)
        return _result as MutableLiveData<ResponseResult>
    }

    fun solutions(idDisease: String): LiveData<ResponseDiseaseSolution> {
        _solutions = MutableLiveData()
        getSolutions(idDisease)
        return _solutions as MutableLiveData<ResponseDiseaseSolution>
    }


    fun resetingConsult(idConsult: String): LiveData<ResponseConsult> {
        _consult = MutableLiveData()
        resetConsult(idConsult)
        return _consult as MutableLiveData<ResponseConsult>
    }


    private fun getSolutions(idDisease: String) {
        val client = ApiConfig.getApiService().detailDisease(idDisease)
        client.enqueue(object : Callback<ResponseDiseaseSolution> {
            override fun onResponse(
                call: Call<ResponseDiseaseSolution>,
                response: Response<ResponseDiseaseSolution>
            ) {
                if (response.isSuccessful) {
                    _solutions?.postValue(response.body())
                } else {
                    val gson = Gson().fromJson(
                        response.errorBody()?.string(),
                        ResponseDiseaseSolution::class.java
                    )
                    _solutions?.postValue(gson)
                }
            }

            override fun onFailure(call: Call<ResponseDiseaseSolution>, t: Throwable) {
                _solutions?.postValue(null)
                Log.e("Response Failure", t.message ?: "")
            }
        })
    }

    private fun getResult(idConsult: String) {
        val client = ApiConfig.getApiService().resultConsult(idConsult)
        client.enqueue(object : Callback<ResponseResult> {
            override fun onResponse(
                call: Call<ResponseResult>,
                response: Response<ResponseResult>
            ) {
                if (response.isSuccessful) {
                    _result?.postValue(response.body())
                } else {
                    val gson =
                        Gson().fromJson(response.errorBody()?.string(), ResponseResult::class.java)
                    _result?.postValue(gson)
                }
            }

            override fun onFailure(call: Call<ResponseResult>, t: Throwable) {
                _result?.postValue(null)
                Log.e("Failure Response", t.message ?: "")
            }
        })
    }

    private fun resetConsult(idConsult: String) {
        val client = ApiConfig.getApiService().resetConsult(idConsult)
        client.enqueue(object : Callback<ResponseConsult> {
            override fun onResponse(
                call: Call<ResponseConsult>,
                response: Response<ResponseConsult>
            ) {
                if (response.isSuccessful) {
                    _consult?.postValue(response.body())
                } else {
                    val error = Gson().fromJson(
                        response.errorBody()?.string(),
                        ResponseConsult::class.java
                    )
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