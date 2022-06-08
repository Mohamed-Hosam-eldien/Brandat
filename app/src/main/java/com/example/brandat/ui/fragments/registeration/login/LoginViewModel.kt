package com.example.brandat.ui.fragments.registeration.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brandat.data.repos.user.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val IRepo: IUserRepository) : ViewModel(){
    private val _signInSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var signInSuccess: LiveData<Boolean> = _signInSuccess
}