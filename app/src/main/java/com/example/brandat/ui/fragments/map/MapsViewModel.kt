package com.example.brandat.ui.fragments.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.ProductsRepository
import com.example.brandat.models.CustomerAddress
import com.example.brandat.models.Addresses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
   private var repository: ProductsRepository
):ViewModel() {

    lateinit var addresses : Addresses

    fun insertAddress(customerAddress: CustomerAddress) {

       viewModelScope.launch(Dispatchers.IO) {
            repository.insertAddress(customerAddress)
        }
    }

}