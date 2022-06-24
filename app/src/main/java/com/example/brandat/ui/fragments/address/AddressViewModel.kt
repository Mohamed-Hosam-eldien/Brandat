package com.example.brandat.ui.fragments.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.data.repos.products.ProductsRepository
import com.example.brandat.data.repos.user.IUserRepository
import com.example.brandat.models.CustomerAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject public constructor(
    var repository: IUserRepository
) : ViewModel() {

    private var _getAddresses = MutableLiveData<List<CustomerAddress>>()
    var getAddresses: LiveData<List<CustomerAddress>> = _getAddresses

    fun getAllAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            var result = repository.getAllAddresses()
            _getAddresses.postValue(result)
        }
    }

    fun insertAddress(customerAddress: CustomerAddress) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAddress(customerAddress)

        }
    }

    fun removeAddress(city: String) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.removeAddress(city)
        }

    }

}