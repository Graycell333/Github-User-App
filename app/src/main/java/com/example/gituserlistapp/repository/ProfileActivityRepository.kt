package com.example.gituserlistapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gituserlistapp.models.UserProfile
import com.example.mvvmkotlinexample.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ProfileActivityRepository {

    val profileDataGetterSetter = MutableLiveData<UserProfile>()

    fun getProfileApiCall(userId: String): MutableLiveData<UserProfile> {

        val call = RetrofitClient.apiInterface.getProfile("users/"+userId)

        call.enqueue(object: Callback<UserProfile> {
            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<UserProfile>,
                response: Response<UserProfile>
            ) {
                // TODO("Not yet implemented")
                Log.v("Response : ", response.body().toString())


                val data = response.body()


                profileDataGetterSetter.value = response.body();
            }
        })

        return profileDataGetterSetter
    }
}