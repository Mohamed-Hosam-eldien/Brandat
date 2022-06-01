package com.example.brandat.ui.fragments.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    val positionMutable = MutableLiveData<Int>()

    fun setPosition(position: Int) {
        positionMutable.value = position
    }

    fun getPosition() : MutableLiveData<Int>{
        return positionMutable
    }



}