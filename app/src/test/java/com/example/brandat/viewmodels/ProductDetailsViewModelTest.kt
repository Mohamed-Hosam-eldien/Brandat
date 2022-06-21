package com.example.brandat.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.brandat.FakeDataSource
import com.example.brandat.FakeRepo
import com.example.brandat.data.source.local.ILocalDataSource
import com.example.brandat.data.source.remote.IRemoteDataSource
import com.example.brandat.models.Brand
import com.example.brandat.models.ProductDetails
import com.example.brandat.ui.fragments.home.BrandViewModel
import getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val instanceTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var  productViewModel : BrandViewModel

   // val repoLocalList = mutableListOf<Brand>(Brand(), Brand(), Brand(), Brand())
    val repoRemoteList = mutableListOf<Brand>(Brand(),Brand(), Brand(), Brand())
    lateinit var fakeRemoteDataSource: IRemoteDataSource
    lateinit var fakeLocalDataSource: ILocalDataSource

    @Before
    fun setup(){
        //Given brandViewModel

        val app = ApplicationProvider.getApplicationContext<Application>()
        //fakeLocalDataSource = FakeDataSource(repoLocalList)
        fakeRemoteDataSource = FakeDataSource(repoRemoteList)

        val repo = FakeRepo()
        productViewModel = BrandViewModel(repo, app)
    }

    @Test
    fun getAllBrandsFromAPI_listOfBrands() {

        //When get all brands
        productViewModel.getBrands()
        val value = productViewModel.brandResponse.getOrAwaitValue { }

        //Then the list has value
        assertNotNull(value)


    }


    @Test
    fun getGetProduct_() {
    }

    @Test
    fun setGetProduct() {
    }


}