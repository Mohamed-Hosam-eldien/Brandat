package com.example.brandat.ui.fragments.registeration.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.user.IUserRepository
import com.example.brandat.models.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val IRepo: IUserRepository) : ViewModel() {
    private val _signUpSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var signUpSuccess: LiveData<Boolean> = _signUpSuccess

    suspend fun registerCustomer(customer: Customer) = viewModelScope.launch {
        IRepo.registerCustomer(customer)
    }


}