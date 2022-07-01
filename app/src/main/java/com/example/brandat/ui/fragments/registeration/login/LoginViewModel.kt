package com.example.brandat.ui.fragments.registeration.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.user.IUserRepository
import com.example.brandat.models.Customer
import com.example.brandat.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val IRepo: IUserRepository) : ViewModel() {
    private val _signInSuccess: MutableLiveData<List<Customer>> = MutableLiveData()
    var signInSuccess: LiveData<List<Customer>> = _signInSuccess
    private var _setError = MutableLiveData<String>()
    var getError: LiveData<String> = _setError

    fun loginCustomer(email: String, tags: String) = viewModelScope.launch {
        when (val result = IRepo.loginCustomer(email, tags)) {
            is NetworkResult.Success -> _signInSuccess.postValue(result.data?.customer)
            is NetworkResult.Error -> _setError.postValue(result.exception)
            else -> {}
        }
    }
}