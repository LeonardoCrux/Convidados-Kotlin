package com.pi.convidadoskotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pi.convidadoskotlin.model.GuestModel
import com.pi.service.repository.GuestRepository

class GuestViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository.getInstance(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(id: Int){
        if(id == 2 ) {
            mGuestList.value = mGuestRepository.getAll()
        } else if (id == 1 ){
            mGuestList.value = mGuestRepository.getPresent()
        } else if(id ==0){
            mGuestList.value = mGuestRepository.getAbsent()
        }
    }

    fun delete(id:Int){
        mGuestRepository.delete(id)
    }

    }

