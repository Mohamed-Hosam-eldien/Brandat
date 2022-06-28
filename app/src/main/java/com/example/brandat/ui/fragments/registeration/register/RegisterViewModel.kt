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
import com.example.brandat.models.CustomerRegisterModel
import com.example.brandat.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val IRepo: IUserRepository) : ViewModel() {
    private val _signUpSuccess: MutableLiveData<Customer> = MutableLiveData()
    var signUpSuccess: LiveData<Customer> = _signUpSuccess
    private var _setError = MutableLiveData<String>()
   fun registerCustomer(customer: CustomerRegisterModel) = viewModelScope.launch {
      val result=IRepo.registerCustomer(customer)
       when(result){
           is NetworkResult.Success-> _signUpSuccess.postValue(result.data?.customer)
           is NetworkResult.Error->  _setError.postValue(result.exception)
       }

    }

}