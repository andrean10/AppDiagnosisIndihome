package com.kontrakanprojects.appbekamcbr.view.info.daftarpenyakit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.kontrakanprojects.appbekamcbr.model.ResponseDisease
import com.kontrakanprojects.appbekamcbr.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarPenyakitViewModel {
    private var _diseases: MutableLiveData<ResponseDisease>? = null

    fun getListDisease(): LiveData<ResponseDisease>{
        _diseases = MutableLiveData<ResponseDisease>()
        listDisease()
        return _diseases as MutableLiveData<ResponseDisease>
    }

    private fun listDisease(){
        val client = ApiConfig.getApiService().disease()
        client.enqueue(object : Callback<ResponseDisease> {
            override fun onResponse(call: Call<ResponseDisease>, response: Response<ResponseDisease>) {
                if (response.isSuccessful) {
                    _diseases?.postValue(response.body())
                } else {
                    val error = Gson().fromJson(response.errorBody()?.string(), ResponseDisease::class.java)
                    _diseases?.postValue(error)
                }
            }

            override fun onFailure(call: Call<ResponseDisease>, t: Throwable) {
                Log.e("Failure Response ", t.message ?: "")
            }
        })
    }
}