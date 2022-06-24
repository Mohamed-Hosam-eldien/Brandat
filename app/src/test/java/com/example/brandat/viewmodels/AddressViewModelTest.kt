package com.example.brandat.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.brandat.data.repo.FakeProductRepo
import com.example.brandat.data.repo.FakeUserRepo
import com.example.brandat.data.source.FakeLocalDataSource
import com.example.brandat.data.source.FakeRemoteDataSource
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Brand
import com.example.brandat.models.Brands
import com.example.brandat.models.CustomerAddress
import com.example.brandat.ui.fragments.address.AddressViewModel
import com.example.brandat.ui.fragments.home.BrandViewModel
import getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddressViewModelTest {

    @get:Rule
    val instanceTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var  addressViewModel : AddressViewModel

    // val repoLocalList = mutableListOf<Brand>(Brand(), Brand(), Brand(), Brand())
    val listOfaddresses : List<CustomerAddress>? = listOf(CustomerAddress(), CustomerAddress(), CustomerAddress())
    val repoRemoteList = listOfaddresses

    //lateinit var fakeRemoteDataSource: IRemoteDataSource
    lateinit var fakeLocalDataSource: ILocalDataSource

    @Before
    fun setup(){
        //Given brandViewModel

       var fakeDataSource = FakeLocalDataSource()
        fakeDataSource._addresses = repoRemoteList as MutableList<CustomerAddress>
        fakeLocalDataSource = fakeDataSource

        val repo = FakeUserRepo()
        addressViewModel = AddressViewModel(repo)
    }


    @Test
    fun getAllAddressFromDB_listOfAddresses(){

        //When get all brands
        addressViewModel.getAllAddress()
        val value = addressViewModel.getAddresses.getOrAwaitValue { }

        //Then the list has value
        Assert.assertNotNull(value)


    }

}