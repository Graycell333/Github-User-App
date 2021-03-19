package com.example.gituserlistapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gituserlistapp.models.UserProfile
import com.example.gituserlistapp.repository.ProfileActivityRepository

class ProfileActivityViewModel  : ViewModel() {

    var servicesProfileLiveData: MutableLiveData<UserProfile>? = null

    fun getProfile(userId: String): LiveData<UserProfile>? {
        servicesProfileLiveData = ProfileActivityRepository.getProfileApiCall(userId)
        return servicesProfileLiveData
    }

}