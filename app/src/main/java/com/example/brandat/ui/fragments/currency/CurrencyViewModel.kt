package com.example.brandat.ui.fragments.currency

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brandat.data.repos.products.IProductsRepository
import com.example.brandat.models.currency.CurrencyResponse
import com.example.brandat.utils.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
 @HiltViewModel
class CurrencyViewModel @Inject constructor(private var repository: IProductsRepository):ViewModel() {


     private var _currency = MutableLiveData<ResponseResult<CurrencyResponse>>()
     var currency: LiveData<ResponseResult<CurrencyResponse>> = _currency

//     private var _currency = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
//     var currency: StateFlow<CurrencyEvent> = _currency
//     fun convertCurrency(){
//    Log.e(TAG, "convertCurrency: ", )
//        viewModelScope.launch {
//           val res = repository.currencyConverter()
//
//            _currency.postValue(res)
//
//
//
//        }
//    }



     sealed class CurrencyEvent{
         class Success(val result: String):CurrencyEvent()
         class Error(val error: String):CurrencyEvent()
         object Empty:CurrencyEvent()

     }

}