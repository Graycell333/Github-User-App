package com.example.mvvmkotlinexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gituserlistapp.models.UserList
import com.example.mvvmkotlinexample.repository.MainActivityRepository

class MainActivityViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<List<UserList>>? = null

    fun getUser(mPage: Int): LiveData<List<UserList>>? {
        servicesLiveData = MainActivityRepository.getServicesApiCall(mPage)
        return servicesLiveData
    }

}