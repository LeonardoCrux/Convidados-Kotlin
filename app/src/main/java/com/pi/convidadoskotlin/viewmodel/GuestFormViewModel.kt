package com.pi.convidadoskotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pi.convidadoskotlin.model.GuestModel
import com.pi.service.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {
    private val mGuestRepository = GuestRepository.getInstance(application.applicationContext)

    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = mSaveGuest
    private var mGuest = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = mGuest

    fun save(id: Int, name: String, presence: Boolean) {

        if(id == 0){
            mSaveGuest.value = mGuestRepository.save(GuestModel(id, name, presence))
        }else {
            mSaveGuest.value = mGuestRepository.update(GuestModel(id, name, presence))
        }
    }

    fun loadData(id: Int){
        mGuest.value =mGuestRepository.get(id)
    }

}