package com.example.mvvmkotlinexample.retrofit

import com.example.gituserlistapp.models.UserList
import com.example.gituserlistapp.models.UserProfile
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @GET("users?")
    fun getUsers( @Query("since") page: Int): Call<List<UserList>>

    @GET
    fun getProfile(@Url id: String): Call<UserProfile>

}