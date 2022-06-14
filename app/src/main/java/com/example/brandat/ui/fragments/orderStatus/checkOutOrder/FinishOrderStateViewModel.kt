package com.example.brandat.ui.fragments.orderStatus.checkOutOrder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class FinishOrderStateViewModel @Inject constructor() :ViewModel() {
    fun confirmOrder() {
    }

    val loading = MutableLiveData<Boolean>()





}