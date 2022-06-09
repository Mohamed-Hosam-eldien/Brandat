package com.example.brandat.ui.fragments.registeration.register

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.user.IUserRepository
import com.example.brandat.models.Customer
import com.example.brandat.models.CustomerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val IRepo: IUserRepository) : ViewModel() {
    private val _signUpSuccess: MutableLiveData<CustomerModel> = MutableLiveData()
    var signUpSuccess: LiveData<CustomerModel> = _signUpSuccess

   fun registerCustomer(customer: CustomerModel) = viewModelScope.launch {
        _signUpSuccess.postValue(IRepo.registerCustomer(customer).body())
    }


}