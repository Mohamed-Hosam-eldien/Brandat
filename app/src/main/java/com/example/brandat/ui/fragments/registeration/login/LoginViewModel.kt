package com.example.brandat.ui.fragments.registeration.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.user.IUserRepository
import com.example.brandat.models.CustomerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val IRepo: IUserRepository) : ViewModel(){
    private val _signInSuccess: MutableLiveData<CustomerModel> = MutableLiveData()
    var signInSuccess: LiveData<CustomerModel> = _signInSuccess

     fun loginCustomer(email:String, tags:String) =viewModelScope.launch {
         _signInSuccess.postValue(IRepo.loginCustomer(email, tags).body())
     }
    }