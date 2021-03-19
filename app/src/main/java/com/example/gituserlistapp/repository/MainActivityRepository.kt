package com.example.mvvmkotlinexample.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gituserlistapp.models.UserList
import com.example.mvvmkotlinexample.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainActivityRepository {

    val serviceSetterGetter = MutableLiveData<List<UserList>>()

    fun getServicesApiCall(mPage: Int): MutableLiveData<List<UserList>> {

        val call = RetrofitClient.apiInterface.getUsers(mPage)

        call.enqueue(object: Callback<List<UserList>> {
            override fun onFailure(call: Call<List<UserList>>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<UserList>>,
                response: Response<List<UserList>>
            ) {
                // TODO("Not yet implemented")
                Log.v("Response : ", response.body().toString())


                val data = response.body()


                serviceSetterGetter.value = response.body();
            }
        })

        return serviceSetterGetter
    }
}