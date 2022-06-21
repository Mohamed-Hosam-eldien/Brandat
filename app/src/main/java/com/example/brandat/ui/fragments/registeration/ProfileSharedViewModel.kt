package com.example.brandat.ui.fragments.registeration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileSharedViewModel: ViewModel() {

    val sharedData = MutableLiveData<Int>()

    fun setCount(count:Int) {
        sharedData.value = count
    }

}